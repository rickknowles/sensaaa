package sensaaa.api.exception.mapper;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import sensaaa.api.exception.NotLoggedInException;

import com.sun.jersey.api.client.ClientResponse.Status;

@Provider
public class NotLoggedInExceptionMapper implements ExceptionMapper<NotLoggedInException> {

    @Override
    public Response toResponse(NotLoggedInException ex) {
        return Response.status(Status.UNAUTHORIZED).build();
    }
    
}
