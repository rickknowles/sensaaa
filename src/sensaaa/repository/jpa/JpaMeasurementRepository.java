package sensaaa.repository.jpa;


import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import sensaaa.domain.Measurement;
import sensaaa.repository.MeasurementRepository;

@Repository
@Component
public class JpaMeasurementRepository implements MeasurementRepository {

    @PersistenceContext
    private EntityManager em;

    public Measurement getById(Long id) {
        Query q = em.createQuery("SELECT sr FROM Measurement sr WHERE sr.id = :id");
        q.setParameter("id", id);
        return (Measurement) q.getSingleResult();
    }

    @SuppressWarnings("unchecked")
    public List<Measurement> listAll() {
        return (List<Measurement>) em.createQuery(
                "SELECT sr FROM Measurement sr ORDER BY sr.createdTime, sr.id").getResultList();
    }

    public Measurement saveOrUpdate(Measurement m) {
        if (em.contains(m)) {
            return em.merge(m);
        } else {
            em.persist(m);
            em.refresh(m);
            return m;
        }
    }

    public void delete(Measurement m) {
        em.remove(m);
    }
}
