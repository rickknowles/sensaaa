package sensaaa.api.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.sun.jersey.api.client.ClientResponse.Status;

@Provider
public class NotOwnerExceptionMapper implements ExceptionMapper<NotOwnerException> {

    @Override
    public Response toResponse(NotOwnerException ex) {
        return Response.status(Status.FORBIDDEN).build();
    }
    
}
