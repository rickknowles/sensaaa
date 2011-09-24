package sensaaa.repository;


import java.util.List;
import java.util.Map;

import sensaaa.domain.MeasurementStream;
import sensaaa.domain.Sensor;

public interface SensorRepository {

	public Sensor getById(Long id);
	
	public List<Sensor> listAll();
	
	public List<MeasurementStream> listMeasurementStreamsBySensor(Sensor sensor);
	
	public Map<String,MeasurementStream> mapMeasurementStreamsByKey(Sensor sensor);
	
    public Sensor saveOrUpdate(Sensor s);
    
    public void delete(Sensor s);
}
