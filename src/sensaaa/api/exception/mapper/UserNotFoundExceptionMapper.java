package sensaaa.api.exception.mapper;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import sensaaa.api.exception.UserNotFoundException;

import com.sun.jersey.api.client.ClientResponse.Status;

@Provider
public class UserNotFoundExceptionMapper implements ExceptionMapper<UserNotFoundException> {

    @Override
    public Response toResponse(UserNotFoundException ex) {
        return Response.status(Status.NOT_FOUND).build();
    }
    
}
