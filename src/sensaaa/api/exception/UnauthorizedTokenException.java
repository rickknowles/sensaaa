package sensaaa.api.exception;

import sensaaa.domain.Sensor;

public class UnauthorizedTokenException extends Exception {

    private final String tokenUsed;
    private final Sensor sensor;

    public UnauthorizedTokenException(String tokenUsed, Sensor sensor) {
        super();
        this.tokenUsed = tokenUsed;
        this.sensor = sensor;
    }

    public String getTokenUsed() {
        return tokenUsed;
    }

    public Sensor getSensor() {
        return sensor;
    }
}
