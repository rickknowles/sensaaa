package sensaaa.repository;


import java.util.List;

import sensaaa.domain.SensorGroup;

public interface SensorGroupRepository {

    public SensorGroup getById(Long id);
    
    public List<SensorGroup> listAll();

    public SensorGroup saveOrUpdate(SensorGroup sg);
    
    public void delete(SensorGroup sg);
}
