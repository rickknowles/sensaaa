package sensaaa.api;


import java.util.List;
import java.util.Set;

import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.joda.time.DateTime;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import sensaaa.api.exception.NotLoggedInException;
import sensaaa.api.exception.NotOwnerException;
import sensaaa.api.exception.ValidationFailureException;
import sensaaa.authorization.AuthorizationService;
import sensaaa.domain.SensorGroup;
import sensaaa.repository.SensorGroupRepository;
import sensaaa.token.TokenGenerator;
import sensaaa.validation.types.SensorGroupName;
import sensaaa.view.types.UserPair;

@Path("/sensor/group")
@Component
public class SensorGroupResource {
//    private final Log log = LogFactory.getLog(SensorGroupResource.class);
    
    @Inject
    private SensorGroupRepository sensorGroupRepository; 
    
    @Inject
    private AuthorizationService authorizationService;
    
    @Inject
    private TokenGenerator tokenGenerator;
    
    @Inject
    private Validator validator;
    
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
    
    @POST
    @Path("register")
    @Produces("application/json")
    @Transactional
    public SensorGroup registerSensorGroup(@FormParam("name") SensorGroupName name) 
            throws NotLoggedInException, ValidationFailureException {
        if (this.validator != null) {
            Set<ConstraintViolation<SensorGroupName>> errors = this.validator.validate(name);
            if (!errors.isEmpty()) {
                throw new ValidationFailureException("sensorGroupName", errors);
            }
        }
        
        UserPair loggedIn = authorizationService.getLoggedInUser();
        if (loggedIn == null) {
            throw new NotLoggedInException();
        }
        SensorGroup sg = new SensorGroup();
        sg.setName(name.getValue());
        sg.setAuthorizedUserId(loggedIn.getLocal().getId());
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
        if (!sg.getAuthorizedUserId().equals(loggedIn.getLocal().getId())) {
            throw new NotOwnerException(loggedIn.getLocal(), sg.getAuthorizedUserId());
        }
        sensorGroupRepository.delete(sg);
        return sg;
    }
}
