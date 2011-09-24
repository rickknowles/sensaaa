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
import sensaaa.view.types.UserPair;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;

@Component
public class GoogleAuthorizationService implements AuthorizationService {
    private final Log log = LogFactory.getLog(GoogleAuthorizationService.class);

    @Inject
    private AuthorizedUserRepository sensorOwnerRepository;
    
    @Inject
    private TokenGenerator tokenGenerator;

    @Inject
    private UserService userService;
    
    @Override
    public UserPair getLoggedInUser() {
        if (!userService.isUserLoggedIn()) {
            return null;
        }
        User user = userService.getCurrentUser();
        log.info("User service reports: id=" + user.getUserId() + " email=" + user.getEmail() + " nickname=" + user.getNickname());
        try {
            AuthorizedUser owner = sensorOwnerRepository.getByUserId(user.getUserId());
            log.info("Loading existing user: " + user.getEmail() + " id=" + owner.getId());
            return new UserPair(owner, user);
        } catch (EmptyResultDataAccessException err) {
            log.info("Registering new user: " + user.getEmail());
            AuthorizedUser owner = new AuthorizedUser();
            owner.setGoogleUserId(user.getUserId());
            owner.setCreatedTime(new DateTime());
            owner.setAccessToken(tokenGenerator.createToken());
            return new UserPair(owner, user);
        }
    }
    
    public String getLoginRedirectURL(String originalURL) {
        return this.userService.createLoginURL(originalURL);
    }
    
    public String getLogoutRedirectURL(String originalURL) {
        return this.userService.createLogoutURL(originalURL);
    }

}