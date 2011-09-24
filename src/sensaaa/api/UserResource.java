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

import org.springframework.stereotype.Component;

import sensaaa.api.exception.NotLoggedInException;
import sensaaa.authorization.AuthorizationService;
import sensaaa.domain.AuthorizedUser;
import sensaaa.repository.AuthorizedUserRepository;
import sensaaa.view.types.UserPair;

@Path("/user")
@Component
public class UserResource {
//    private final Log log = LogFactory.getLog(SensorResource.class);
    
    @Inject
    private AuthorizedUserRepository authorizedUserRepository; 
    
    @Inject
    private AuthorizationService authorizationService;
    
    @GET
    @Path("current")
    @Produces("application/json")
    public UserPair getLoggedInUser() throws NotLoggedInException {
        UserPair me = authorizationService.getLoggedInUser();
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
        return Response.status(Status.TEMPORARY_REDIRECT).header("Location", this.authorizationService.getLoginRedirectURL(redirectTo)).build();
    }
    
    @GET
    @Path("logout")
    public Response logout(@QueryParam("redirectTo") @DefaultValue("/") String redirectTo) {
        return Response.status(Status.TEMPORARY_REDIRECT).header("Location", this.authorizationService.getLogoutRedirectURL(redirectTo)).build();
    }
//    @GET
//    @Path("login")
//    public Response loginStart(
//            @QueryParam("hd") @DefaultValue("") String domain,
//            @Context HttpServletRequest request
//            ) throws Exception {
//        if (!domain.equals("")) {
//            // User attempting to login with provided domain, build and OpenID request and redirect
//            try {
//                IdpIdentifier openId = new IdpIdentifier(domain);
//
//                String realm = realm(request);
//                String returnToUrl = returnTo(request);
//
//                AuthRequestHelper helper = consumerHelper.getAuthRequestHelper(openId, returnToUrl);
//                helper.requestAxAttribute(Step2.AxSchema.EMAIL, true)
//                        .requestAxAttribute(Step2.AxSchema.FIRST_NAME, true)
//                        .requestAxAttribute(Step2.AxSchema.LAST_NAME, true);
//
//                HttpSession session = request.getSession();
//                AuthRequest authReq = helper.generateRequest();
//                authReq.setRealm(realm);
//
//                UiMessageRequest uiExtension = new UiMessageRequest();
//                uiExtension.setIconRequest(true);
//                authReq.addExtension(uiExtension);
//
//                session.setAttribute("discovered", helper.getDiscoveryInformation());
//
//                return Response.status(Status.TEMPORARY_REDIRECT)
//                               .header("Location", authReq.getDestinationUrl(true)).build();
//            } catch (OpenIDException e) {
//                throw new ServletException("Error initializing OpenID request", e);
//            }
//        } else {
//            // This is a response from the provider, go ahead and validate
//            return openIDLoginComplete(request);
//        }
//        
//    }
//    
//    @POST
//    @Path("login")
//    public Response completeOpenIDLogin(
//            @Context HttpServletRequest request
//            ) throws Exception {
//        UserInfo userInfo = null;
//        try {
//            
//            HttpSession session = request.getSession();
//            ParameterList openidResp = Step2.getParameterList(request);
//            String receivingUrl = Step2.getUrlWithQueryString(request);
//            DiscoveryInformation discovered =
//                    (DiscoveryInformation) session.getAttribute("discovered");
//
//
//            AuthResponseHelper authResponse = consumerHelper.verify(receivingUrl, openidResp, discovered);
//            if (authResponse.getAuthResultType() == AuthResponseHelper.ResultType.AUTH_SUCCESS) {
//                userInfo = new UserInfo(
//                        consumerHelper.getClaimedId().toString(),
//                        consumerHelper.getAxFetchAttributeValue(Step2.AxSchema.EMAIL),
//                        consumerHelper.getAxFetchAttributeValue(Step2.AxSchema.FIRST_NAME),
//                        consumerHelper.getAxFetchAttributeValue(Step2.AxSchema.LAST_NAME));
//            } else {
//                userInfo = null;
//            }
//            
//            
//            request.getSession().setAttribute("user", userInfo);
//            return Response.status(Status.TEMPORARY_REDIRECT).header("Location", homePath).build();
//        } catch (OpenIDException e) {
//            throw new ServletException("Error initializing OpenID request", e);
//        }
//    }

}
