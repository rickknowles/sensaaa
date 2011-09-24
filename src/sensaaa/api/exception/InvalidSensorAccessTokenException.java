package sensaaa.api.exception;

import sensaaa.domain.Sensor;

public class InvalidSensorAccessTokenException extends Exception {

    private final String userToken;
    private final Sensor sensor;
    
    public InvalidSensorAccessTokenException(String userToken, Sensor sensor) {
        super();
        this.userToken = userToken;
        this.sensor = sensor;
    }

    public String getUserToken() {
        return userToken;
    }

    public Sensor getSensor() {
        return sensor;
    }
}
