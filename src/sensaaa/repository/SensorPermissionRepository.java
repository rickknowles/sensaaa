package sensaaa.repository;

import java.util.List;

import sensaaa.domain.SensorPermission;

public interface SensorPermissionRepository {

    public SensorPermission getById(Long id);
    
    public List<SensorPermission> listAll();
    
    public List<SensorPermission> listAllSensorPermissions(long sensorId);
    
    public List<SensorPermission> listAllSensorGroupPermissions(long sensorGroupId);
    
    public List<SensorPermission> listAllSensorPermissionsForUser(long userId, boolean includeGroups, boolean includePublic);
    
    public List<SensorPermission> listAllSensorGroupPermissionsForUser(long userId, boolean includePublic);
    
    public List<SensorPermission> listAllForPublic(boolean includeGroups, boolean includeSensors);

    public boolean hasPermissionOnSensorGroup(long sensorGroupId, long userId);

    public boolean hasPermissionOnSensor(long sensorId, long userId);

    public boolean isSensorGroupPubliclyVisible(long sensorGroupId);

    public boolean isSensorPubliclyVisible(long sensorId);

    public SensorPermission saveOrUpdate(SensorPermission sp);
    
    public void delete(SensorPermission sp);

}
