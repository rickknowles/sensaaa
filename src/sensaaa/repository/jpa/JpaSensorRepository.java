package sensaaa.repository.jpa;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import sensaaa.domain.MeasurementStream;
import sensaaa.domain.Sensor;
import sensaaa.domain.SensorPermission;
import sensaaa.repository.SensorPermissionRepository;
import sensaaa.repository.SensorRepository;
import sensaaa.repository.TagRepository;

@Repository
@Component
public class JpaSensorRepository implements SensorRepository {

    @PersistenceContext
    private EntityManager em;
    
    @Inject 
    private SensorPermissionRepository sensorPermissionRepository;
    
    @Inject 
    private TagRepository tagRepository;

    public Sensor getById(long sensorId) {
        Query q = em.createQuery("SELECT s FROM Sensor s WHERE s.id = :id");
        q.setParameter("id", sensorId);
        return (Sensor) q.getSingleResult();
    }

    @SuppressWarnings("unchecked")
    public List<Sensor> listAll() {
        return (List<Sensor>) em.createQuery(
                "SELECT s FROM Sensor s ORDER BY s.id").getResultList();
    }

    @SuppressWarnings("unchecked")
    public List<Sensor> listAllVisibleToUser(long userId) {
        List<SensorPermission> perms = sensorPermissionRepository.listAllSensorPermissionsForUser(userId, true, true);
        if (!perms.isEmpty()) {
            Set<Long> sensorIds = new HashSet<Long>();
            for (SensorPermission sp : perms) {
                sensorIds.add(sp.getSensorId());
            }
            
            Query q = em.createQuery("SELECT sg FROM SensorGroup sg WHERE sg.id IN (:sensorIds) ORDER BY sg.id");
            q.setParameter("sensorIds", sensorIds);
            return (List<Sensor>) q.getResultList();
        } else {
            return new ArrayList<Sensor>();
        }
    }

    @SuppressWarnings("unchecked")
    public List<Sensor> listSensorsByGroupId(long sensorGroupId) {
        Query q = em.createQuery("SELECT s FROM Sensor s WHERE s.sensorGroupId = :sensorGroupId ORDER BY s.id");
        q.setParameter("sensorGroupId", sensorGroupId);
        return (List<Sensor>) q.getResultList();
    } 
    
    @SuppressWarnings("unchecked")
    public List<Sensor> listAllVisibleToPublic(boolean includePublicGroups) {
        // Would very much like to do this in a way that isn't so iterative/heavy, but appengine's JPA API is 
        // so primitive I'm left without much choice.        
        List<SensorPermission> perms = sensorPermissionRepository.listAllForPublic(includePublicGroups, true);
        if (!perms.isEmpty()) {
            Set<Long> sensorIds = new HashSet<Long>();
            Set<Long> sensorGroupIds = new HashSet<Long>();
            for (SensorPermission sp : perms) {
                if (sp.getSensorId() != null) {
                    sensorIds.add(sp.getSensorId());
                } else if (includePublicGroups && sp.getSensorGroupId() != null) {
                    sensorGroupIds.add(sp.getSensorGroupId());
                }
            }
            
            Query q = em.createQuery(
                    "SELECT s FROM Sensor s " +
                    "WHERE s.id IN (:sensorIds) " +
                    (sensorGroupIds.isEmpty() ? "" : "OR s.sensorGroupId IN (:sensorGroupIds) ") + 
                    "ORDER BY s.id");
            q.setParameter("sensorIds", sensorIds);
            q.setParameter("sensorGroupIds", sensorGroupIds);
            return (List<Sensor>) q.getResultList();
        } else {
            return new ArrayList<Sensor>();
        }
    }

    @SuppressWarnings("unchecked")
    public List<MeasurementStream> listMeasurementStreamsBySensor(Sensor sensor) {
        Query q = em.createQuery(
                "SELECT sds FROM SensorDataStream WHERE sds.sensorId = ? ORDER BY sds.id");
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
        tagRepository.deleteTagAssocationsBySensor(s.getId());
        for (SensorPermission sp : sensorPermissionRepository.listAllSensorPermissions(s.getId())) {
            sensorPermissionRepository.delete(sp);
        }
        em.remove(s);
    }
}
