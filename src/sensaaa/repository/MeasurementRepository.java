package sensaaa.repository;


import java.util.List;

import sensaaa.domain.Measurement;

public interface MeasurementRepository {

    public Measurement getById(Long id);
    
    public List<Measurement> listAll();

    public Measurement saveOrUpdate(Measurement m);
    
    public void delete(Measurement m);
}
