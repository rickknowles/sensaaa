package sensaaa.repository.jpa;

import javax.jdo.annotations.Transactional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import sensaaa.domain.SensorTagAssociation;
import sensaaa.domain.Tag;
import sensaaa.repository.TagRepository;

@Repository
@Component
public class JpaTagRepository implements TagRepository {

    @PersistenceContext
    private EntityManager em;

    public Tag findByName(String tagName) {
        Query q = em.createQuery("SELECT t FROM Tag t WHERE t.name = ?");
        q.setParameter(1, tagName.toLowerCase());
        return (Tag) q.getSingleResult();
    }
    
    @Transactional
    public Tag saveOrUpdate(Tag t) {
        if (em.contains(t)) {
            return em.merge(t);
        } else {
            em.persist(t);
            return t;
        }
    }
    
    @Transactional
    public void delete(Tag t) {
        em.remove(t);
    }
    
    public SensorTagAssociation saveOrUpdateSensorTagAssociation(SensorTagAssociation sta) {
        if (em.contains(sta)) {
            return em.merge(sta);
        } else {
            em.persist(sta);
            em.refresh(sta);
            return sta;
        }        
    }
    
    public void deleteTagAssocationsBySensor(long sensorId) {
        Query q = em.createQuery("DELETE FROM SensorTagAssociation sta WHERE sta.sensorId = ?");
        q.setParameter(1, sensorId);
        q.executeUpdate();
    }
}
