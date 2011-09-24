package sensaaa.authorization;

import sensaaa.view.types.UserPair;

public interface AuthorizationService {
    public UserPair getLoggedInUser();
    
    public String getLoginRedirectURL(String originalURL);
    
    public String getLogoutRedirectURL(String originalURL);
}
