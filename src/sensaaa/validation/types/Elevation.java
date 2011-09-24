package sensaaa.validation.types;

import javax.validation.constraints.Min;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.codehaus.jackson.annotate.JsonValue;
import org.hibernate.validator.constraints.NotEmpty;

public class Elevation {

    private Double value;

    public Elevation() {}

    public Elevation(Double value) {
        this();
        setValue(value);
    }

    @JsonValue
    @NotEmpty
    @Min(value = 0)
    public Double getValue() {
        return value;
    }

    private void setValue(Double value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Elevation{" + value + "}";
    }

    public static Elevation valueOf(String s) {
        return new Elevation(Double.parseDouble(s));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || !(o instanceof Elevation)) {
            return false;
        }

        Elevation other = (Elevation) o;

        return new EqualsBuilder().append(value, other.value).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(value).toHashCode();
    }

}
