package sensaaa.api.exception;

import java.util.Set;

public class ValidationFailureException extends Exception {

    private final String fieldName;
    private final Set<?> errors;

    public ValidationFailureException(String fieldName, Set<?> errors) {
        super("Validation errors (" + fieldName + "): " + errors);
        this.fieldName = fieldName;
        this.errors = errors;
    }

    public String getFieldName() {
        return fieldName;
    }

    public Set<?> getErrors() {
        return errors;
    }
    
    
}
