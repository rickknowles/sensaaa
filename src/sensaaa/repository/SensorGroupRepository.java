package sensaaa.repository;


import java.util.List;

import sensaaa.domain.SensorGroup;

public interface SensorGroupRepository {

    public SensorGroup getById(long id);
    
    public List<SensorGroup> listAll();
    
    public List<SensorGroup> listAllVisibleToPublic();
    
    public List<SensorGroup> listAllVisibleToUser(long userId);

    public SensorGroup saveOrUpdate(SensorGroup sg);
    
    public void delete(SensorGroup sg);
}
