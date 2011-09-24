package sensaaa.api.exception;

import sensaaa.domain.AuthorizedUser;

public class NotOwnerException extends Exception {

    private final AuthorizedUser loggedIn;
    private final AuthorizedUser owner;
    
    public NotOwnerException(AuthorizedUser loggedIn, AuthorizedUser owner) {
        super();
        this.owner = owner;
        this.loggedIn = loggedIn;
    }

    public AuthorizedUser getLoggedIn() {
        return loggedIn;
    }

    public AuthorizedUser getOwner() {
        return owner;
    }
    
    
}
