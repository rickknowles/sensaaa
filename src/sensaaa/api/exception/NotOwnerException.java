package sensaaa.api.exception;

import sensaaa.domain.AuthorizedUser;

public class NotOwnerException extends Exception {

    private final AuthorizedUser loggedIn;
    private final Long ownerId;
    
    public NotOwnerException(AuthorizedUser loggedIn, Long ownerId) {
        super();
        this.ownerId = ownerId;
        this.loggedIn = loggedIn;
    }

    public AuthorizedUser getLoggedIn() {
        return loggedIn;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    
    
}
