package sensaaa.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.joda.time.DateTime;

@Entity
public class SensorGroup {

	private Long id;
	private String name;
	private Long authorizedUserId;
    private DateTime createdTime;
    private String accessToken;
    private boolean visibleToAll;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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
    public Long getAuthorizedUserId() {
        return authorizedUserId;
    }
    public void setAuthorizedUserId(Long authorizedUserId) {
        this.authorizedUserId = authorizedUserId;
    }
    public String getAccessToken() {
        return accessToken;
    }
    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
    public boolean isVisibleToAll() {
        return visibleToAll;
    }
    public void setVisibleToAll(boolean visibleToAll) {
        this.visibleToAll = visibleToAll;
    }
	
}
