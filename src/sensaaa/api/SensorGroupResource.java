package sensaaa.api;


import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.joda.time.DateTime;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import sensaaa.api.exception.NotLoggedInException;
import sensaaa.api.exception.NotOwnerException;
import sensaaa.authorization.AuthorizationService;
import sensaaa.domain.SensorGroup;
import sensaaa.repository.SensorGroupRepository;
import sensaaa.token.TokenGenerator;
import sensaaa.view.types.UserPair;

@Path("/sensor/group")
@Component
public class SensorGroupResource {
//    private final Log log = LogFactory.getLog(SensorResource.class);
    
    @Inject
    private SensorGroupRepository sensorGroupRepository; 
    
    @Inject
    private AuthorizationService authorizationService;
    
    @Inject
    private TokenGenerator tokenGenerator;
    
    @GET
    @Path("list")
    @Produces("application/json")
    public List<SensorGroup> list() {
        return sensorGroupRepository.listAll();
    }
    
    @GET
    @Path("{id}")
    @Produces("application/json")
    public SensorGroup getById(@PathParam("id") Long id) {
        return sensorGroupRepository.getById(id);
    }
    
    @PUT
    @Path("register")
    @Produces("application/json")
    @Transactional
    public SensorGroup registerSensorGroup(@FormParam("name") String name) throws NotLoggedInException {
        UserPair loggedIn = authorizationService.getLoggedInUser();
        if (loggedIn == null) {
            throw new NotLoggedInException();
        }
        SensorGroup sg = new SensorGroup();
        sg.setName(name);
        sg.setAuthorizedUser(loggedIn.getLocal());
        sg.setCreatedTime(new DateTime());
        sg.setAccessToken(tokenGenerator.createToken());
        return sensorGroupRepository.saveOrUpdate(sg);
    }
    
    @DELETE
    @Path("{id}")
    @Produces("application/json")
    @Transactional
    public SensorGroup deleteSensorGroup(@PathParam("id") Long id) throws NotLoggedInException, NotOwnerException {
        UserPair loggedIn = authorizationService.getLoggedInUser();
        if (loggedIn == null) {
            throw new NotLoggedInException();
        }
        
        SensorGroup sg = this.sensorGroupRepository.getById(id);
        if (!sg.getAuthorizedUser().getId().equals(loggedIn.getLocal().getId())) {
            throw new NotOwnerException(loggedIn.getLocal(), sg.getAuthorizedUser());
        }
        sensorGroupRepository.delete(sg);
        return sg;
    }
}
