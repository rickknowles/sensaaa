package sensaaa.api.exception.mapper;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import sensaaa.api.exception.UnauthorizedTokenException;

import com.sun.jersey.api.client.ClientResponse.Status;

@Provider
public class UnauthorizedTokenExceptionMapper implements ExceptionMapper<UnauthorizedTokenException> {

    @Override
    public Response toResponse(UnauthorizedTokenException ex) {
        return Response.status(Status.FORBIDDEN)
                        .type(MediaType.APPLICATION_JSON)
                        .entity(ex.getTokenUsed()).build();
    }
    
}
