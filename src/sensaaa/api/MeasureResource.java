package sensaaa.api;


import java.io.StringReader;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.joda.time.DateTime;
import org.python.core.PyObject;
import org.python.util.PythonInterpreter;
import org.springframework.stereotype.Component;

import sensaaa.api.exception.InvalidFormatCSVReadingException;
import sensaaa.api.exception.InvalidSensorAccessTokenException;
import sensaaa.api.exception.UnauthorizedTokenException;
import sensaaa.domain.Measurement;
import sensaaa.domain.MeasurementStream;
import sensaaa.domain.Sensor;
import sensaaa.repository.MeasurementRepository;
import sensaaa.repository.SensorRepository;

@Path("/measure")
@Component
public class MeasureResource {
    private final Log log = LogFactory.getLog(MeasureResource.class);
    
    @Inject
    private SensorRepository sensorRepository;
    
    @Inject
    private MeasurementRepository measurementRepository;
    
    @POST
    @Path("{id}")
    @Consumes(MediaType.TEXT_PLAIN) 
    public List<Measurement> receiveMeasurement(
            @PathParam("id") Long sensorId,
            @HeaderParam("X-access-token") @DefaultValue("") String accessToken,
            String requestBody
            ) throws UnauthorizedTokenException, InvalidSensorAccessTokenException, InvalidFormatCSVReadingException {
        DateTime now = new DateTime();
        Sensor sensor = sensorRepository.getById(sensorId);
        
        // check access token
        if (!accessToken.equals(sensor.getAccessToken())) {
            throw new InvalidSensorAccessTokenException(accessToken, sensor);
        } else if (sensor.getParseScript() == null) {
            throw new RuntimeException("Error: no parse script defined for sensor " + sensorId);
        }
        
        List<MeasurementStream> streams = sensorRepository.listMeasurementStreamsBySensor(sensor);
        List<Measurement> out = new ArrayList<Measurement>();
        
        // Add any elements that are constant to the tree here
        StringWriter stdErr = new StringWriter();
        StringWriter stdOut = new StringWriter();
        
        long startTime = System.currentTimeMillis();
        try {
            PythonInterpreter python = new PythonInterpreter();
            python.setIn(new StringReader(requestBody));
            python.setOut(stdOut);
            python.setErr(stdErr);
            python.exec(sensor.getParseScript());
            if (!stdErr.toString().equals("")) {
                log.warn("StdErr from jython script:\n" + stdErr.toString());
            }
            if (!stdOut.toString().equals("")) {
                log.debug("StdOut from jython script:\n" + stdOut.toString());
            }
            for (MeasurementStream stream : streams) {
                PyObject obj = python.get(stream.getKey());
                if (obj == null) {
                    continue;
                }
                Measurement m = new Measurement();
                m.setMeasurementStreamId(stream.getId());
                m.setCreatedTime(now);
                m.setReading(new BigDecimal(obj.toString()));
                m = measurementRepository.saveOrUpdate(m);
                out.add(m);
            }
            return out;
        } finally {
            log.debug("Executed python script in " + (System.currentTimeMillis() - startTime) + "ms");
        }
    }
}
