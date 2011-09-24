package sensaaa.api.exception;

import sensaaa.domain.SensorGroup;

public class NotPermittedToEditSensorGroupException extends Exception {
    private final SensorGroup sensorGroup;

    public NotPermittedToEditSensorGroupException(SensorGroup sensorGroup) {
        super();
        this.sensorGroup = sensorGroup;
    }

    public SensorGroup getSensorGroup() {
        return sensorGroup;
    }
}
