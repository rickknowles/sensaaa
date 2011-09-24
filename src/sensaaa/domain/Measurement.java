package sensaaa.domain;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.joda.time.DateTime;

import com.google.appengine.api.datastore.Key;

@Entity
public class Measurement {

	private Key id;
	private MeasurementStream measurementStream;
	private BigDecimal reading;
	
	private Double latitude;
	private Double longitude;
    private Double elevation;
    private boolean indoor;
    
    private DateTime createdTime;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Key getId() {
		return id;
	}
	public void setId(Key id) {
		this.id = id;
	}
	@ManyToOne(targetEntity = MeasurementStream.class)
    public MeasurementStream getMeasurementStream() {
        return measurementStream;
    }
    public void setMeasurementStream(MeasurementStream measurementStream) {
        this.measurementStream = measurementStream;
    }
	public BigDecimal getReading() {
		return reading;
	}
	public void setReading(BigDecimal reading) {
		this.reading = reading;
	}
    public DateTime getCreatedTime() {
        return createdTime;
    }
    public void setCreatedTime(DateTime createdTime) {
        this.createdTime = createdTime;
    }
    public Double getLatitude() {
        return latitude;
    }
    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }
    public Double getLongitude() {
        return longitude;
    }
    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
    public Double getElevation() {
        return elevation;
    }
    public void setElevation(Double elevation) {
        this.elevation = elevation;
    }
    public boolean isIndoor() {
        return indoor;
    }
    public void setIndoor(boolean indoor) {
        this.indoor = indoor;
    }
	
}
