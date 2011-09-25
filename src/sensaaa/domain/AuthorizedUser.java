package sensaaa.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.joda.time.DateTime;

import com.google.appengine.api.datastore.Key;

@Entity
public class AuthorizedUser {

    private Key id;
    private String googleUserId;
    private DateTime createdTime;
    private String accessToken;
    private DateTime approvedTimestamp;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Key getId() {
        return id;
    }

    public void setId(Key id) {
        this.id = id;
    }

    public String getGoogleUserId() {
        return googleUserId;
    }

    public void setGoogleUserId(String googleUserId) {
        this.googleUserId = googleUserId;
    }

    public DateTime getApprovedTimestamp() {
        return approvedTimestamp;
    }

    public void setApprovedTimestamp(DateTime approvedTimestamp) {
        this.approvedTimestamp = approvedTimestamp;
    }

    public DateTime getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(DateTime createdTime) {
        this.createdTime = createdTime;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

}
