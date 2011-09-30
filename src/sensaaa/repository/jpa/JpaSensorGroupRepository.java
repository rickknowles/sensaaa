package sensaaa.repository.jpa;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import sensaaa.domain.Sensor;
import sensaaa.domain.SensorGroup;
import sensaaa.domain.SensorPermission;
import sensaaa.repository.SensorGroupRepository;
import sensaaa.repository.SensorPermissionRepository;
import sensaaa.repository.SensorRepository;

@Repository
@Component
public class JpaSensorGroupRepository implements SensorGroupRepository {

    @PersistenceContext
    private EntityManager em;
    
    @Inject
    private SensorRepository sensorRepository;
    
    @Inject 
    private SensorPermissionRepository sensorPermissionRepository;

    public SensorGroup getById(long id) {
        Query q = em.createQuery("SELECT sg FROM SensorGroup sg WHERE sg.id = :id");
        q.setParameter("id", id);
        return (SensorGroup) q.getSingleResult();
    }

    @SuppressWarnings("unchecked")
    public List<SensorGroup> listAllVisibleToUser(long userId) {
        List<SensorPermission> perms = sensorPermissionRepository.listAllSensorGroupPermissionsForUser(userId, true);
        if (!perms.isEmpty()) {
            Set<Long> sensorGroupIds = new HashSet<Long>();
            for (SensorPermission sp : perms) {
                sensorGroupIds.add(sp.getSensorGroupId());
            }
            
            Query q = em.createQuery("SELECT sg FROM SensorGroup sg WHERE sg.id IN (:sensorGroupIds) ORDER BY sg.id");
            q.setParameter("sensorGroupIds", sensorGroupIds);
            return (List<SensorGroup>) q.getResultList();
        } else {
            return new ArrayList<SensorGroup>();
        }
    }

    @SuppressWarnings("unchecked")
    public List<SensorGroup> listAllVisibleToPublic() {
        List<SensorPermission> perms = sensorPermissionRepository.listAllForPublic(true, false);
        if (!perms.isEmpty()) {
            Set<Long> sensorGroupIds = new HashSet<Long>();
            for (SensorPermission sp : perms) {
                sensorGroupIds.add(sp.getSensorGroupId());
            }
            
            Query q = em.createQuery("SELECT sg FROM SensorGroup sg WHERE sg.id IN (:sensorGroupIds) ORDER BY sg.id");
            q.setParameter("sensorGroupIds", sensorGroupIds);
            return (List<SensorGroup>) q.getResultList();
        } else {
            return new ArrayList<SensorGroup>();
        }
    }

    @SuppressWarnings("unchecked")
    public List<SensorGroup> listAll() {
        return (List<SensorGroup>) em.createQuery(
                "SELECT sg FROM SensorGroup sg ORDER BY sg.id").getResultList();
    }

    public SensorGroup saveOrUpdate(SensorGroup sg) {
        if (em.contains(sg)) {
            return em.merge(sg);
        } else {
            em.persist(sg);
            em.refresh(sg);
            return sg;
        }
    }

    public void delete(SensorGroup sg) {
        for (Sensor s : sensorRepository.listSensorsByGroupId(sg.getId())) {
            sensorRepository.delete(s);
        }
        for (SensorPermission sp : sensorPermissionRepository.listAllSensorGroupPermissions(sg.getId())) {
            sensorPermissionRepository.delete(sp);
        }
        em.remove(sg);
    }
}
