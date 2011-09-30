package sensaaa.api.exception.mapper;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import sensaaa.api.exception.SensorNotFoundException;

import com.sun.jersey.api.client.ClientResponse.Status;

@Provider
public class SensorNotFoundExceptionMapper implements ExceptionMapper<SensorNotFoundException> {

    @Override
    public Response toResponse(SensorNotFoundException ex) {
        return Response.status(Status.NOT_FOUND).build();
    }
    
}
