package sensaaa.view.types;

import java.util.List;

import sensaaa.domain.AuthorizedUser;
import sensaaa.domain.Sensor;
import sensaaa.domain.SensorGroup;
import sensaaa.domain.Tag;

public class SensorView {

    private Sensor sensor;
    private SensorGroup sensorGroup;
    private AuthorizedUser sensorOwner;
    private List<Tag> sensorTags;
    
    public Sensor getSensor() {
        return sensor;
    }
    public void setSensor(Sensor sensor) {
        this.sensor = sensor;
    }
    public SensorGroup getSensorGroup() {
        return sensorGroup;
    }
    public void setSensorGroup(SensorGroup sensorGroup) {
        this.sensorGroup = sensorGroup;
    }
    public AuthorizedUser getSensorOwner() {
        return sensorOwner;
    }
    public void setSensorOwner(AuthorizedUser sensorOwner) {
        this.sensorOwner = sensorOwner;
    }
    public List<Tag> getSensorTags() {
        return sensorTags;
    }
    public void setSensorTags(List<Tag> sensorTags) {
        this.sensorTags = sensorTags;
    }
}
