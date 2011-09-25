package sensaaa.api;


import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.joda.time.DateTime;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import sensaaa.api.exception.NotLoggedInException;
import sensaaa.api.exception.NotOwnerException;
import sensaaa.api.exception.NotPermittedToEditSensorGroupException;
import sensaaa.api.exception.SensorNotFoundException;
import sensaaa.authorization.AuthorizationService;
import sensaaa.domain.Sensor;
import sensaaa.domain.SensorGroup;
import sensaaa.repository.SensorGroupRepository;
import sensaaa.repository.SensorRepository;
import sensaaa.token.TokenGenerator;
import sensaaa.validation.types.Elevation;
import sensaaa.validation.types.Latitude;
import sensaaa.validation.types.Longitude;
import sensaaa.validation.types.SensorName;
import sensaaa.view.types.UserPair;

@Path("/sensor")
@Component
public class SensorResource {
//    private final Log log = LogFactory.getLog(SensorResource.class);
    
    @Inject
    private SensorRepository sensorRepository; 
    
    @Inject
    private SensorGroupRepository sensorGroupRepository; 
    
    @Inject
    private TokenGenerator tokenGenerator;
    
    @Inject
    private AuthorizationService authorizationService;
    
    @GET
    @Path("list")
    @Produces("application/json")
    public List<Sensor> list() {
        UserPair loggedIn = authorizationService.getLoggedInUser();
        if (loggedIn == null) {        
            return sensorRepository.listAllPublic();
        } else {
            return sensorRepository.listAllForUser(loggedIn.getLocal().getId().getId());
        }
    }
    
    @GET
    @Path("{id}")
    @Produces("application/json")
    public Sensor getById(@PathParam("id") Long id) throws SensorNotFoundException {
        UserPair loggedIn = authorizationService.getLoggedInUser();
        if (loggedIn == null) {        
            Sensor s = sensorRepository.getById(id);
            if (s == null || !s.isVisibleToPublic()) {
                throw new SensorNotFoundException(id);
            } else {
                return s;
            }
        } else {
            Sensor s = sensorRepository.getByIdForUser(id, loggedIn.getLocal().getId().getId());
            if (s == null) {
                throw new SensorNotFoundException(id);
            } else {
                return s;
            }
        }
    }
    
    @PUT
    @Path("add")
    @Produces("application/json")
    @Transactional
    public Sensor add(
            @FormParam("name") @DefaultValue("") SensorName sensorName, 
            @FormParam("indoor") boolean indoor, 
            @FormParam("latitude") Latitude latitude,
            @FormParam("longitude") Longitude longitude,
            @FormParam("elevation") Elevation elevation,
            @FormParam("groupId") Long groupId) throws NotLoggedInException, NotPermittedToEditSensorGroupException {
        // Validate
        
        UserPair loggedIn = authorizationService.getLoggedInUser();
        if (loggedIn == null) {
            throw new NotLoggedInException();
        }
        
        SensorGroup sensorGroup = this.sensorGroupRepository.getById(groupId);
        if (!sensorGroup.getAuthorizedUser().getId().equals(loggedIn.getLocal().getId())) {
            throw new NotPermittedToEditSensorGroupException(sensorGroup);
        }
        
        Sensor sensor = new Sensor();
        sensor.setSensorGroup(sensorGroup);
        sensor.setAccessToken(tokenGenerator.createToken());
        sensor.setCreatedTime(new DateTime());
        sensor.setName(sensorName.getValue());
        sensor.setIndoor(indoor);
        sensor.setLatitude(latitude == null ? null : latitude.getValue());
        sensor.setLongitude(longitude == null ? null : longitude.getValue());
        sensor.setElevation(elevation == null ? null : elevation.getValue());
        return this.sensorRepository.saveOrUpdate(sensor);
    }
    
    @DELETE
    @Path("{id}")
    @Produces("application/json")
    @Transactional
    public Sensor deleteSensor(@PathParam("id") Long id) throws NotLoggedInException, NotOwnerException {
        UserPair loggedIn = authorizationService.getLoggedInUser();
        if (loggedIn == null) {
            throw new NotLoggedInException();
        }
        
        Sensor s = this.sensorRepository.getById(id);
        if (!s.getSensorGroup().getAuthorizedUser().getId().equals(loggedIn.getLocal().getId())) {
            throw new NotOwnerException(loggedIn.getLocal(), s.getSensorGroup().getAuthorizedUser());
        }
        sensorRepository.delete(s);
        return s;
    }
}
