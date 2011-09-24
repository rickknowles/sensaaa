package sensaaa.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.joda.time.DateTime;

import com.google.appengine.api.datastore.Key;

@Entity
public class SensorGroup {

	private Key id;
	private String name;
	private AuthorizedUser authorizedUser;
    private DateTime createdTime;
    private String accessToken;
    private boolean visibleToAll;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Key getId() {
		return id;
	}
	public void setId(Key id) {
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
    public AuthorizedUser getAuthorizedUser() {
        return authorizedUser;
    }
    public void setAuthorizedUser(AuthorizedUser authorizedUser) {
        this.authorizedUser = authorizedUser;
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
