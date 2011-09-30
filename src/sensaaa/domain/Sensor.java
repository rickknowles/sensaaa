package sensaaa.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.joda.time.DateTime;

@Entity
public class Sensor {

	private Long id;
	private Long sensorGroupId;
	private String name;
	
	private String calloutUrl;
	
    private Double latitude;
    private Double longitude;
    private Double elevation;
    private boolean indoor;
    
	private DateTime createdTime;
	private String accessToken;
	
	private String parseScript;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getSensorGroupId() {
		return sensorGroupId;
	}
	public void setSensorGroupId(Long sensorGroupId) {
		this.sensorGroupId = sensorGroupId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
    public String getAccessToken() {
        return accessToken;
    }
    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
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
    public String getCalloutUrl() {
        return calloutUrl;
    }
    public void setCalloutUrl(String calloutUrl) {
        this.calloutUrl = calloutUrl;
    }
    public String getParseScript() {
        return parseScript;
    }
    public void setParseScript(String parseScript) {
        this.parseScript = parseScript;
    }
}
