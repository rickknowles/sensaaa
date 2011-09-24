package sensaaa.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.joda.time.DateTime;

import com.google.appengine.api.datastore.Key;

@Entity
public class SensorTagAssociation {

	private Key id;
	private Sensor sensor;
	private Tag tag;
	private DateTime createdTime;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Key getId() {
		return id;
	}
	public void setId(Key id) {
		this.id = id;
	}
    public Sensor getSensor() {
        return sensor;
    }
    public void setSensor(Sensor sensor) {
        this.sensor = sensor;
    }
    public Tag getTag() {
        return tag;
    }
    public void setTag(Tag tag) {
        this.tag = tag;
    }
    public DateTime getCreatedTime() {
        return createdTime;
    }
    public void setCreatedTime(DateTime createdTime) {
        this.createdTime = createdTime;
    }
	
}
