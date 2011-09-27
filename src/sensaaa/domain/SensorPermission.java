package sensaaa.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.joda.time.DateTime;

@Entity
public class SensorPermission {

    private Long id;
    private Long sensorId;
    private Long sensorGroupId;
    private Long userId;
    private DateTime createdTime;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Long getSensorId() {
        return sensorId;
    }
    public void setSensorId(Long sensorId) {
        this.sensorId = sensorId;
    }
    public Long getSensorGroupId() {
        return sensorGroupId;
    }
    public void setSensorGroupId(Long sensorGroupId) {
        this.sensorGroupId = sensorGroupId;
    }
    public Long getUserId() {
        return userId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    public DateTime getCreatedTime() {
        return createdTime;
    }
    public void setCreatedTime(DateTime createdTime) {
        this.createdTime = createdTime;
    }

}
