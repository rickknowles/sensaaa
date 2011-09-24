package sensaaa.repository.jpa;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import sensaaa.domain.MeasurementStream;
import sensaaa.domain.Sensor;
import sensaaa.repository.SensorRepository;

@Repository
@Component
public class JpaSensorRepository implements SensorRepository {

    @PersistenceContext
    private EntityManager em;

    public Sensor getById(Long id) {
        Query q = em.createQuery("SELECT s FROM Sensor s WHERE s.id = ?");
        q.setParameter(1, id);
        return (Sensor) q.getSingleResult();
    }

    @SuppressWarnings("unchecked")
    public List<Sensor> listAll() {
        return (List<Sensor>) em.createQuery(
                "SELECT s FROM Sensor s ORDER BY s.createdTime, s.id").getResultList();
    }

    @SuppressWarnings("unchecked")
    public List<MeasurementStream> listMeasurementStreamsBySensor(Sensor sensor) {
        Query q = em.createQuery(
                "SELECT sds FROM SensorDataStream sds.sensor.id = ? " +
                "WHERE ORDER BY sds.createdTime, sds.id");
        q.setParameter(1, sensor.getId());
        return (List<MeasurementStream>) q.getResultList();
    }

    public Map<String,MeasurementStream> mapMeasurementStreamsByKey(Sensor sensor) {
        Map<String,MeasurementStream>  map = new HashMap<String,MeasurementStream>();
        for (MeasurementStream sds : listMeasurementStreamsBySensor(sensor)) {
            map.put(sds.getKey(), sds);
        }
        return map;
    }
    
    public Sensor saveOrUpdate(Sensor s) {
        if (em.contains(s)) {
            return em.merge(s);
        } else {
            em.persist(s);
            return s;
        }
    }

    public void delete(Sensor s) {
        em.remove(s);
    }
}
