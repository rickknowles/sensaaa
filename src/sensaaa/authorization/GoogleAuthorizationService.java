package sensaaa.authorization;


import javax.inject.Inject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.joda.time.DateTime;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;

import sensaaa.domain.AuthorizedUser;
import sensaaa.repository.AuthorizedUserRepository;
import sensaaa.token.TokenGenerator;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;

@Component
public class GoogleAuthorizationService implements AuthorizationService {
    private final Log log = LogFactory.getLog(GoogleAuthorizationService.class);

    @Inject
    private AuthorizedUserRepository authorizedUserRepository;
    
    @Inject
    private TokenGenerator tokenGenerator;

    @Inject
    private UserService userService;
    
    @Override
    public AuthorizedUser getLoggedInUser() {
        if (!userService.isUserLoggedIn()) {
            return null;
        }
        User user = userService.getCurrentUser();
        log.info("User service reports: id=" + user.getUserId() + " email=" + user.getEmail());
        try {
            AuthorizedUser owner = authorizedUserRepository.getByUserId(user.getUserId());
            if (owner.getEmail() == null || !owner.getEmail().equalsIgnoreCase(user.getEmail())) {
                log.info("Updating user email to " + user.getEmail());
                owner.setEmail(user.getEmail());
                authorizedUserRepository.saveOrUpdate(owner);
            }
            log.info("Loading existing user: " + user.getEmail() + " id=" + owner.getId());
            return owner;
        } catch (EmptyResultDataAccessException err) {
            log.info("Registering new user: " + user.getEmail());
            AuthorizedUser owner = new AuthorizedUser();
            owner.setGoogleUserId(user.getUserId());
            owner.setCreatedTime(new DateTime());
            owner.setAccessToken(tokenGenerator.createToken());
            owner.setEmail(user.getEmail());
            authorizedUserRepository.saveOrUpdate(owner);
            return owner;
        }
    }
    
    public String getLoginRedirectURL(String originalURL) {
        return this.userService.createLoginURL(originalURL);
    }
    
    public String getLogoutRedirectURL(String originalURL) {
        return this.userService.createLogoutURL(originalURL);
    }

}