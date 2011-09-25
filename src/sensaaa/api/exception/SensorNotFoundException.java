package sensaaa.api.exception;

public class SensorNotFoundException extends Exception {

    private final Long sensorId;

    public SensorNotFoundException(Long sensorId) {
        super();
        this.sensorId = sensorId;
    }

    public Long getSensorId() {
        return sensorId;
    }

}
