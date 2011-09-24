package sensaaa.view.types;

import sensaaa.domain.AuthorizedUser;

import com.google.appengine.api.users.User;

public class UserPair {
    private AuthorizedUser local;
    private User remote;
    
    public UserPair(AuthorizedUser local, User remote) {
        super();
        this.local = local;
        this.remote = remote;
    }

    public AuthorizedUser getLocal() {
        return local;
    }
    
    public String getEmail() {
        return this.remote.getEmail();
    }
    
    public String getNickname() {
        return this.remote.getNickname();
    }
}
