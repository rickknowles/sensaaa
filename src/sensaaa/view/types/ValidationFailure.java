package sensaaa.view.types;

public class ValidationFailure {

    private String fieldName;
    private Object value;
    private String message;
    
    public ValidationFailure() {
        super();
    }
    public ValidationFailure(String fieldName, Object value, String message) {
        super();
        this.fieldName = fieldName;
        this.value = value;
        this.message = message;
    }
    public String getFieldName() {
        return fieldName;
    }
    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }
    public Object getValue() {
        return value;
    }
    public void setValue(Object value) {
        this.value = value;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
}
