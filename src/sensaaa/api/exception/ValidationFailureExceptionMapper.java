package sensaaa.api.exception;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintViolation;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import sensaaa.view.types.ValidationFailure;

@Provider
public class ValidationFailureExceptionMapper implements ExceptionMapper<ValidationFailureException> {

    @Override
    public Response toResponse(ValidationFailureException exception) {
        List<ValidationFailure> msgs = new ArrayList<ValidationFailure>();
        for (Object e : exception.getErrors()) {
            ConstraintViolation<?> cv = ((ConstraintViolation<?>) e);
            msgs.add(new ValidationFailure(exception.getFieldName(), cv.getInvalidValue(), cv.getMessage()));
        }
        return Response.status(Status.BAD_REQUEST).entity(msgs).type(MediaType.APPLICATION_JSON).build();
    }
}
