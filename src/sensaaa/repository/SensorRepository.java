package sensaaa.repository;


import java.util.List;
import java.util.Map;

import sensaaa.domain.MeasurementStream;
import sensaaa.domain.Sensor;

public interface SensorRepository {

	public Sensor getById(long id);
	
	public List<Sensor> listAll();
    
    public List<Sensor> listAllVisibleToPublic(boolean includePublicGroups);
    
    public List<Sensor> listAllVisibleToUser(long userId);
    
    public List<Sensor> listSensorsByGroupId(long sensorGroupId);
	
	public List<MeasurementStream> listMeasurementStreamsBySensor(Sensor sensor);
	
	public Map<String,MeasurementStream> mapMeasurementStreamsByKey(Sensor sensor);
	
    public Sensor saveOrUpdate(Sensor s);
    
    public void delete(Sensor s);
}
