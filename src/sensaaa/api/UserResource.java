package sensaaa.api;


import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.joda.time.DateTime;
import org.springframework.stereotype.Component;

import sensaaa.api.exception.NotLoggedInException;
import sensaaa.api.exception.UserNotFoundException;
import sensaaa.authorization.AuthorizationService;
import sensaaa.domain.AuthorizedUser;
import sensaaa.repository.AuthorizedUserRepository;

@Path("/user")
@Component
public class UserResource {
    private final Log log = LogFactory.getLog(UserResource.class);
    
    @Inject
    private AuthorizedUserRepository authorizedUserRepository; 
    
    @Inject
    private AuthorizationService authorizationService;
    
    @GET
    @Path("current")
    @Produces("application/json")
    public AuthorizedUser getLoggedInUser() throws NotLoggedInException {
        AuthorizedUser me = authorizationService.getLoggedInUser();
        if (me == null) {
            throw new NotLoggedInException();
        }
        return me;
    }
    
    @GET
    @Path("list")
    @Produces("application/json")
    public List<AuthorizedUser> list() {
        return authorizedUserRepository.listAll();
    }
    
    @GET
    @Path("{id}")
    @Produces("application/json")
    public AuthorizedUser getById(@PathParam("id") Long id) {
    	return authorizedUserRepository.getById(id);
    }
    
    @GET
    @Path("login")
    public Response login(@QueryParam("redirectTo") @DefaultValue("/") String redirectTo) {
        return Response.status(Status.TEMPORARY_REDIRECT)
                       .header("Location", this.authorizationService.getLoginRedirectURL(redirectTo))
                       .build();
    }
    
    @GET
    @Path("logout")
    public Response logout(@QueryParam("redirectTo") @DefaultValue("/") String redirectTo) {
        return Response.status(Status.TEMPORARY_REDIRECT)
                       .header("Location", this.authorizationService.getLogoutRedirectURL(redirectTo))
                       .build();
    }
    
    @GET
    @Path("{id}/approve")
    @Produces("application/json")
    public AuthorizedUser approve(@PathParam("id") Long id) throws UserNotFoundException {
        AuthorizedUser user = authorizedUserRepository.getById(id);
        if (user == null) {
            throw new UserNotFoundException(id);
        } else if (user.getApprovedTimestamp() != null) {
            log.warn("User already approved, skipping: " + user);
            return user;
        } else {
            log.warn("User approved, skipping: " + user);
            user.setApprovedTimestamp(new DateTime());
            authorizedUserRepository.saveOrUpdate(user);
            return user;
        }
    }
}
