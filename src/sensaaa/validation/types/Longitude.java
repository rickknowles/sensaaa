package sensaaa.validation.types;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.codehaus.jackson.annotate.JsonValue;
import org.hibernate.validator.constraints.NotEmpty;

public class Longitude {

    private Double value;

    public Longitude() {}

    public Longitude(Double value) {
        this();
        setValue(value);
    }

    @JsonValue
    @NotEmpty
    @Min(value = -90)
    @Max(value = 90)
    public Double getValue() {
        return value;
    }

    private void setValue(Double value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Longitude{" + value + "}";
    }

    public static Longitude valueOf(String s) {
        return new Longitude(Double.parseDouble(s));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || !(o instanceof Longitude)) {
            return false;
        }

        Longitude other = (Longitude) o;

        return new EqualsBuilder().append(value, other.value).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(value).toHashCode();
    }

}
