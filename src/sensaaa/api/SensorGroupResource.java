package sensaaa.api;


import java.util.List;
import java.util.Set;

import javax.inject.Inject;
import javax.jdo.annotations.Transactional;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.joda.time.DateTime;
import org.springframework.stereotype.Component;

import sensaaa.api.exception.NotLoggedInException;
import sensaaa.api.exception.NotOwnerException;
import sensaaa.api.exception.SensorGroupNotFoundException;
import sensaaa.api.exception.ValidationFailureException;
import sensaaa.authorization.AuthorizationService;
import sensaaa.domain.AuthorizedUser;
import sensaaa.domain.SensorGroup;
import sensaaa.domain.SensorPermission;
import sensaaa.repository.SensorGroupRepository;
import sensaaa.repository.SensorPermissionRepository;
import sensaaa.token.TokenGenerator;
import sensaaa.validation.types.SensorGroupName;

@Path("/sensor/group")
@Component
public class SensorGroupResource {
//    private final Log log = LogFactory.getLog(SensorGroupResource.class);
    
    @Inject
    private SensorGroupRepository sensorGroupRepository; 
    
    @Inject 
    private SensorPermissionRepository sensorPermissionRepository;
    
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
        AuthorizedUser loggedIn = authorizationService.getLoggedInUser();
        if (loggedIn != null) {        
            return sensorGroupRepository.listAllVisibleToUser(loggedIn.getId());
        } else {
            return sensorGroupRepository.listAllVisibleToPublic();
        }
    }
    
    @GET
    @Path("{id}")
    @Produces("application/json")
    public SensorGroup getById(@PathParam("id") long id) throws SensorGroupNotFoundException {
        AuthorizedUser loggedIn = authorizationService.getLoggedInUser();
        if (loggedIn != null) {        
            SensorGroup sg = sensorGroupRepository.getById(id);
            if (sensorPermissionRepository.hasPermissionOnSensorGroup(id, loggedIn.getId())) {
                return sg;
            }
        }
        throw new SensorGroupNotFoundException(id); // replace with better exception
    }
    
    @POST
    @Path("register")
    @Produces("application/json")
    @Transactional
    public SensorGroup registerSensorGroup(
            @FormParam("name") SensorGroupName name,
            @FormParam("visibleToPublic") @DefaultValue("false") boolean visibleToPublic) 
            throws NotLoggedInException, ValidationFailureException {
        if (this.validator != null) {
            Set<ConstraintViolation<SensorGroupName>> errors = this.validator.validate(name);
            if (!errors.isEmpty()) {
                throw new ValidationFailureException("sensorGroupName", errors);
            }
        }
        
        AuthorizedUser loggedIn = authorizationService.getLoggedInUser();
        if (loggedIn == null) {
            throw new NotLoggedInException();
        }
        
        SensorGroup sg = new SensorGroup();
        sg.setName(name.getValue());
        sg.setAuthorizedUserId(loggedIn.getId());
        sg.setCreatedTime(new DateTime());
        sg.setAccessToken(tokenGenerator.createToken());
        sg = sensorGroupRepository.saveOrUpdate(sg);
        
        SensorPermission spOwner = new SensorPermission();
        spOwner.setSensorGroupId(sg.getId());
        spOwner.setUserId(loggedIn.getId());
        sensorPermissionRepository.saveOrUpdate(spOwner);
        
        if (visibleToPublic) {
            SensorPermission spPublic = new SensorPermission();
            spPublic.setSensorGroupId(sg.getId());
            spPublic.setVisibleToPublic(true);
            sensorPermissionRepository.saveOrUpdate(spPublic);
        }
        
        return sg;
    }
    
    @DELETE
    @Path("{id}")
    @Produces("application/json")
    @Transactional
    public SensorGroup deleteSensorGroup(@PathParam("id") Long id) throws NotLoggedInException, NotOwnerException {
        AuthorizedUser loggedIn = authorizationService.getLoggedInUser();
        if (loggedIn == null) {
            throw new NotLoggedInException();
        }
        
        SensorGroup sg = this.sensorGroupRepository.getById(id);
        if (!sg.getAuthorizedUserId().equals(loggedIn.getId())) {
            throw new NotOwnerException(loggedIn, sg.getAuthorizedUserId());
        }
        sensorGroupRepository.delete(sg);
        return sg;
    }
}
