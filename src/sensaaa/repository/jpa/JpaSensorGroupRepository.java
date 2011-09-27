package sensaaa.repository.jpa;


import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import sensaaa.domain.SensorGroup;
import sensaaa.repository.SensorGroupRepository;

@Repository
@Component
public class JpaSensorGroupRepository implements SensorGroupRepository {

    @PersistenceContext
    private EntityManager em;

    public SensorGroup getById(long id) {
        Query q = em.createQuery("SELECT sg FROM SensorGroup sg WHERE sg.id = :id");
        q.setParameter("id", id);
        return (SensorGroup) q.getSingleResult();
    }

    public SensorGroup getByIdForUser(long sensorGroupId, long userId) {
        Query q = em.createQuery("SELECT sg FROM SensorGroup sg WHERE sg.id = ? AND (sg.authorizedUserId = ? " +
                "OR sg.id IN (SELECT sp.sensorGroupId FROM SensorPermission sp WHERE sp.userId = ? AND sp.sensorId IS NULL))");
        q.setParameter(1, sensorGroupId);
        q.setParameter(2, userId);
        q.setParameter(3, userId);
        return (SensorGroup) q.getSingleResult();
    }

    @SuppressWarnings("unchecked")
    public List<SensorGroup> listAllForUser(long userId) {
        Query q = em.createQuery("SELECT sg FROM SensorGroup sg WHERE sg.authorizedUserId = ? " +
                "OR sg.id IN (SELECT sp.sensorGroupId FROM SensorPermission sp WHERE sp.userId = ? AND sp.sensorId IS NULL) " + 
                "ORDER BY sg.id");
        q.setParameter(1, userId);
        return (List<SensorGroup>) q.getResultList();
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
            return sg;
        }
    }
    
    public void delete(SensorGroup sg) {
        em.remove(sg);
    }
}
