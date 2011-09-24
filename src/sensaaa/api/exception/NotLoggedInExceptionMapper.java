package sensaaa.api.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.sun.jersey.api.client.ClientResponse.Status;

@Provider
public class NotLoggedInExceptionMapper implements ExceptionMapper<NotLoggedInException> {

    @Override
    public Response toResponse(NotLoggedInException ex) {
        return Response.status(Status.UNAUTHORIZED).build();
    }
    
}
