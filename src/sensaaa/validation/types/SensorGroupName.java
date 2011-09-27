package sensaaa.validation.types;

import javax.validation.constraints.Size;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.annotate.JsonValue;
import org.hibernate.validator.constraints.NotEmpty;

public class SensorGroupName {
    private static final Log log = LogFactory.getLog(SensorGroupName.class);

    private String value;

    public SensorGroupName() {}

    public SensorGroupName(String value) {
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
        return "SensorGroupName{" + value + "}";
    }

    public static SensorGroupName valueOf(String s) {
        log.info("group=" + s);
        return new SensorGroupName(s);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || !(o instanceof SensorGroupName)) {
            return false;
        }

        SensorGroupName other = (SensorGroupName) o;

        return new EqualsBuilder().append(value, other.value).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(value).toHashCode();
    }

}
