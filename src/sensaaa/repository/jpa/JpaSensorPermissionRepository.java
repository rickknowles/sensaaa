package sensaaa.repository.jpa;


import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import sensaaa.domain.SensorPermission;
import sensaaa.repository.SensorPermissionRepository;

@Repository
@Component
public class JpaSensorPermissionRepository implements SensorPermissionRepository {

    @PersistenceContext
    private EntityManager em;

    public SensorPermission getById(Long id) {
        Query q = em.createQuery("SELECT sp FROM SensorPermission sp WHERE sp.id = :id");
        q.setParameter("id", id);
        return (SensorPermission) q.getSingleResult();
    }

    @SuppressWarnings("unchecked")
    public List<SensorPermission> listAll() {
        return (List<SensorPermission>) em.createQuery(
                "SELECT sp FROM SensorPermission sp ORDER BY sr.id").getResultList();
    }

    @SuppressWarnings("unchecked")
    public List<SensorPermission> listAllSensorGroupPermissions(long sensorGroupId) {
        Query q = em.createQuery(
                "SELECT sp FROM SensorPermission sp " +
                "WHERE sp.sensorGroupId = :sensorGroupId " +
                "AND sp.sensorId IS NULL");
        q.setParameter("sensorGroupId", sensorGroupId);
        return (List<SensorPermission>) q.getResultList();
    }

    @SuppressWarnings("unchecked")
    public List<SensorPermission> listAllSensorPermissions(long sensorId) {
        Query q = em.createQuery(
                "SELECT sp FROM SensorPermission sp " +
                "WHERE sp.sensorId = :sensorId " +
                "AND sp.sensorGroupId IS NULL");
        q.setParameter("sensorId", sensorId);
        return (List<SensorPermission>) q.getResultList();
    }

    @SuppressWarnings("unchecked")
    public List<SensorPermission> listAllSensorPermissionsForUser(long userId, boolean includeGroups, boolean includePublic) {
        String sql = "SELECT sp FROM SensorPermission sp " +
                "WHERE sp.userId = :userId ";
        if (!includeGroups && !includePublic) {
            sql += "AND sp.sensorGroupId IS NULL AND sp.visibleToPublic = FALSE";
        } else if (!includeGroups) {
            sql += "AND sp.sensorGroupId IS NULL";
        } else if (!includePublic) {
            sql += "AND sp.visibleToPublic = FALSE";
        }
        Query q = em.createQuery(sql);
        q.setParameter("userId", userId);
        return (List<SensorPermission>) q.getResultList();
    }

    @SuppressWarnings("unchecked")
    public List<SensorPermission> listAllSensorGroupPermissionsForUser(long userId, boolean includePublic) {
        String sql = "SELECT sp FROM SensorPermission sp " +
                "WHERE sp.userId = :userId " +
                "AND sp.sensorId IS NULL ";
        if (!includePublic) {
            sql += "AND sp.visibleToPublic = FALSE";
        }
        Query q = em.createQuery(sql);
        q.setParameter("userId", userId);
        return (List<SensorPermission>) q.getResultList();
    }

    public boolean hasPermissionOnSensorGroup(long sensorGroupId, long userId) {
        Query q = em.createQuery(
                "SELECT sp FROM SensorPermission sp " +
                "WHERE (sp.userId = :userId OR sp.visibleToPublic = TRUE) " +
                "AND sp.sensorGroupId = :sensorGroupId " +
                "AND sp.sensorId IS NULL");
        q.setParameter("sensorGroupId", sensorGroupId);
        q.setParameter("userId", userId);
        q.setMaxResults(1);
        return !q.getResultList().isEmpty();
    }

    public boolean hasPermissionOnSensor(long sensorId, long userId) {
        Query q = em.createQuery(
                "SELECT sp FROM SensorPermission sp " +
                "WHERE (sp.userId = :userId OR sp.visibleToPublic = TRUE) " +
                "AND sp.sensorId = :sensorId " +
                "AND sp.sensorGroupId IS NULL");
        q.setParameter("sensorId", sensorId);
        q.setParameter("userId", userId);
        q.setMaxResults(1);
        return !q.getResultList().isEmpty();
    }

    public boolean isSensorGroupPubliclyVisible(long sensorGroupId) {
        Query q = em.createQuery(
                "SELECT sp FROM SensorPermission sp " +
                "WHERE sp.sensorGroupId = :sensorGroupId " +
                "AND sp.sensorId IS NULL " +
                "AND sp.visibleToPublic = TRUE");
        q.setParameter("sensorGroupId", sensorGroupId);
        q.setMaxResults(1);
        return !q.getResultList().isEmpty();
    }

    public boolean isSensorPubliclyVisible(long sensorId)  {
        Query q = em.createQuery(
                "SELECT sp FROM SensorPermission sp " +
                "WHERE sp.sensorId = :sensorId " +
                "AND sp.sensorGroupId IS NULL " +
                "AND sp.visibleToPublic = TRUE");
        q.setParameter("sensorId", sensorId);
        q.setMaxResults(1);
        return !q.getResultList().isEmpty();
    }
    
    @SuppressWarnings("unchecked")
    public List<SensorPermission> listAllForPublic(boolean includeGroups, boolean includeSensors)  {
        if (!includeGroups && !includeSensors) {
            return new ArrayList<SensorPermission>();
        }
        String sql = "SELECT sp FROM SensorPermission sp " +
                "WHERE sp.visibleToPublic = TRUE ";
        if (includeGroups && !includeSensors) {
            sql += "AND sp.sensorGroupId IS NOT NULL";
        } else if (!includeGroups && includeSensors) {
            sql += "AND sp.sensorId IS NOT NULL";
        }
        return (List<SensorPermission>) em.createQuery(sql).getResultList();
    }

    public SensorPermission saveOrUpdate(SensorPermission sp) {
        if (em.contains(sp)) {
            return em.merge(sp);
        } else {
            em.persist(sp);
            return sp;
        }
    }

    public void delete(SensorPermission sp) {
        em.remove(sp);
    }
}
