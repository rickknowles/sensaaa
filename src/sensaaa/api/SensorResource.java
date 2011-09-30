package sensaaa.api;


import java.util.List;

import javax.inject.Inject;
import javax.jdo.annotations.Transactional;
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
import sensaaa.api.exception.NotPermittedToEditSensorGroupException;
import sensaaa.api.exception.SensorNotFoundException;
import sensaaa.authorization.AuthorizationService;
import sensaaa.domain.AuthorizedUser;
import sensaaa.domain.Sensor;
import sensaaa.domain.SensorGroup;
import sensaaa.domain.SensorPermission;
import sensaaa.domain.SensorTagAssociation;
import sensaaa.domain.Tag;
import sensaaa.repository.SensorGroupRepository;
import sensaaa.repository.SensorPermissionRepository;
import sensaaa.repository.SensorRepository;
import sensaaa.repository.TagRepository;
import sensaaa.token.TokenGenerator;
import sensaaa.validation.types.Elevation;
import sensaaa.validation.types.Latitude;
import sensaaa.validation.types.Longitude;
import sensaaa.validation.types.SensorName;

@Path("/sensor")
@Component
public class SensorResource {
//    private final Log log = LogFactory.getLog(SensorResource.class);
    
    @Inject
    private SensorRepository sensorRepository; 
    
    @Inject
    private SensorGroupRepository sensorGroupRepository; 
    
    @Inject 
    private SensorPermissionRepository sensorPermissionRepository;
    
    @Inject
    private TagRepository tagRepository; 
    
    @Inject
    private TokenGenerator tokenGenerator;
    
    @Inject
    private AuthorizationService authorizationService;
    
    @GET
    @Path("list")
    @Produces("application/json")
    public List<Sensor> list() {
        AuthorizedUser loggedIn = authorizationService.getLoggedInUser();
        if (loggedIn == null) {        
            return sensorRepository.listAllVisibleToPublic(true);
        } else {
            return sensorRepository.listAllVisibleToUser(loggedIn.getId());
        }
    }
    
    @GET
    @Path("{id}")
    @Produces("application/json")
    public Sensor getById(@PathParam("id") Long id) throws SensorNotFoundException {
        AuthorizedUser loggedIn = authorizationService.getLoggedInUser();
        if (loggedIn == null) {
            Sensor s = sensorRepository.getById(id);
            if (s == null || !sensorPermissionRepository.isSensorPubliclyVisible(id)) {
                throw new SensorNotFoundException(id);
            } else {
                return s;
            }
        } else {
            Sensor s = sensorRepository.getById(id);
            if (s == null || !sensorPermissionRepository.hasPermissionOnSensor(s.getId(), loggedIn.getId())) {
                throw new SensorNotFoundException(id);
            } else {
                return s;
            }
        }
    }
    
    @POST
    @Path("add")
    @Produces("application/json")
    @Transactional
    public Sensor add(
            @FormParam("name") @DefaultValue("") SensorName sensorName, 
            @FormParam("indoor") @DefaultValue("false") boolean indoor, 
            @FormParam("latitude") Latitude latitude,
            @FormParam("longitude") Longitude longitude,
            @FormParam("elevation") Elevation elevation,
            @FormParam("tags") @DefaultValue("") String tags,
            @FormParam("parseScript") @DefaultValue("") String parseScript,
            @FormParam("groupId") long groupId,
            @FormParam("visibleToPublic") @DefaultValue("false") boolean visibleToPublic) throws NotLoggedInException, NotPermittedToEditSensorGroupException {
        // Validate
        
        AuthorizedUser loggedIn = authorizationService.getLoggedInUser();
        if (loggedIn == null) {
            throw new NotLoggedInException();
        }
        
        SensorGroup sensorGroup = this.sensorGroupRepository.getById(groupId);
        if (!sensorGroup.getAuthorizedUserId().equals(loggedIn.getId())) {
            throw new NotPermittedToEditSensorGroupException(sensorGroup);
        }
        
        DateTime now = new DateTime();
        
        Sensor sensor = new Sensor();
        sensor.setSensorGroupId(sensorGroup.getId());
        sensor.setAccessToken(tokenGenerator.createToken());
        sensor.setCreatedTime(now);
        sensor.setName(sensorName.getValue());
        sensor.setIndoor(indoor);
        sensor.setParseScript(parseScript);
        sensor.setLatitude(latitude == null ? null : latitude.getValue());
        sensor.setLongitude(longitude == null ? null : longitude.getValue());
        sensor.setElevation(elevation == null ? null : elevation.getValue());
        sensor = this.sensorRepository.saveOrUpdate(sensor);
        
        SensorPermission spOwner = new SensorPermission();
        spOwner.setSensorId(sensor.getId());
        spOwner.setUserId(loggedIn.getId());
        sensorPermissionRepository.saveOrUpdate(spOwner);
        
        if (visibleToPublic) {
            SensorPermission spPublic = new SensorPermission();
            spPublic.setSensorId(sensor.getId());
            spPublic.setVisibleToPublic(true);
            sensorPermissionRepository.saveOrUpdate(spPublic);
        }
        
        // Add tag associations
        if (tags != null && !tags.equals("")) {
            String[] tagArr = tags.split("\\p{Space}");
            for (String tagStr : tagArr) {
                Tag tag = tagRepository.findByName(tagStr);
                if (tag == null) {
                    tag = new Tag();
                    tag.setName(tagStr.toLowerCase());
                    tag.setCreatedTime(now);
                    tag = tagRepository.saveOrUpdate(tag);
                }
                SensorTagAssociation sta = new SensorTagAssociation();
                sta.setSensorId(sensor.getId());
                sta.setTagId(tag.getId());
                sta.setCreatedTime(now);
                tagRepository.saveOrUpdateSensorTagAssociation(sta);
            }
        }
        
        return sensor;
    }
    
    @DELETE
    @Path("{id}")
    @Produces("application/json")
    @Transactional
    public Sensor deleteSensor(@PathParam("id") Long id) throws NotLoggedInException, NotOwnerException {
        AuthorizedUser loggedIn = authorizationService.getLoggedInUser();
        if (loggedIn == null) {
            throw new NotLoggedInException();
        }
        
        Sensor s = this.sensorRepository.getById(id);
        SensorGroup sg = this.sensorGroupRepository.getById(s.getSensorGroupId());
        if (!sg.getAuthorizedUserId().equals(loggedIn.getId())) {
            throw new NotOwnerException(loggedIn, sg.getAuthorizedUserId());
        }
        
        tagRepository.deleteTagAssocationsBySensor(s.getId());
        sensorRepository.delete(s);
        return s;
    }
}
