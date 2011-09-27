package sensaaa.repository;

import sensaaa.domain.SensorTagAssociation;
import sensaaa.domain.Tag;

public interface TagRepository {
    public Tag findByName(String tagName);
    
    public Tag saveOrUpdate(Tag t);
    
    public void delete(Tag t);
    
    public SensorTagAssociation saveOrUpdateSensorTagAssociation(SensorTagAssociation sta);
    
    public void deleteTagAssocationsBySensor(long sensorId);
}
