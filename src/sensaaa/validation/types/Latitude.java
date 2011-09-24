package sensaaa.validation.types;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.codehaus.jackson.annotate.JsonValue;
import org.hibernate.validator.constraints.NotEmpty;

public class Latitude {

    private Double value;

    public Latitude() {}

    public Latitude(Double value) {
        this();
        setValue(value);
    }

    @JsonValue
    @NotEmpty
    @Min(value = -180)
    @Max(value = 180)
    public Double getValue() {
        return value;
    }

    private void setValue(Double value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Latitude{" + value + "}";
    }

    public static Latitude valueOf(String s) {
        return new Latitude(Double.parseDouble(s));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || !(o instanceof Latitude)) {
            return false;
        }

        Latitude other = (Latitude) o;

        return new EqualsBuilder().append(value, other.value).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(value).toHashCode();
    }

}
