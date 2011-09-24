package sensaaa.validation.types;

import javax.validation.constraints.Size;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.codehaus.jackson.annotate.JsonValue;
import org.hibernate.validator.constraints.NotEmpty;

public class SensorName {

    private String value;

    public SensorName() {}

    public SensorName(String value) {
        this();
        setValue(value);
    }

    @JsonValue
    @NotEmpty
    @Size(max = 400)
    public String getValue() {
        return value;
    }

    private void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "SensorName{" + value + "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || !(o instanceof SensorName)) {
            return false;
        }

        SensorName other = (SensorName) o;

        return new EqualsBuilder().append(value, other.value).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(value).toHashCode();
    }

}
