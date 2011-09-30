package sensaaa.authorization;

import sensaaa.domain.AuthorizedUser;

public interface AuthorizationService {
    public AuthorizedUser getLoggedInUser();
    
    public String getLoginRedirectURL(String originalURL);
    
    public String getLogoutRedirectURL(String originalURL);
}
