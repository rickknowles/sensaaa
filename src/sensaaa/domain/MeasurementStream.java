package sensaaa.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.joda.time.DateTime;

@Entity
public class MeasurementStream {

	private Long id;
	private Long sensorId;
	private String key;
	private String units;
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
    public String getKey() {
        return key;
    }
    public void setKey(String key) {
        this.key = key;
    }
    public DateTime getCreatedTime() {
        return createdTime;
    }
    public void setCreatedTime(DateTime createdTime) {
        this.createdTime = createdTime;
    }
    public String getUnits() {
        return units;
    }
    public void setUnits(String units) {
        this.units = units;
    }
	
}
