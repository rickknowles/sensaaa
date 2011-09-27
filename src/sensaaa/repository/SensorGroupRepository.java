package sensaaa.repository;


import java.util.List;

import sensaaa.domain.SensorGroup;

public interface SensorGroupRepository {

    public SensorGroup getById(long id);
    
    public SensorGroup getByIdForUser(long id, long userId);
    
    public List<SensorGroup> listAll();
    
    public List<SensorGroup> listAllForUser(long userId);

    public SensorGroup saveOrUpdate(SensorGroup sg);
    
    public void delete(SensorGroup sg);
}
