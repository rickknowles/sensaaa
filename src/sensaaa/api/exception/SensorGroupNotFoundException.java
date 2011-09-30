package sensaaa.api.exception;

public class SensorGroupNotFoundException extends Exception {

    private final long sensorGroupId;

    public SensorGroupNotFoundException(long sensorGroupId) {
        super();
        this.sensorGroupId = sensorGroupId;
    }

    public long getSensorGroupId() {
        return sensorGroupId;
    }

}
