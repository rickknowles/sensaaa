package sensaaa.api.exception.mapper;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import sensaaa.api.exception.SensorGroupNotFoundException;

import com.sun.jersey.api.client.ClientResponse.Status;

@Provider
public class SensorGroupNotFoundExceptionMapper implements ExceptionMapper<SensorGroupNotFoundException> {

    @Override
    public Response toResponse(SensorGroupNotFoundException ex) {
        return Response.status(Status.NOT_FOUND).build();
    }
    
}
