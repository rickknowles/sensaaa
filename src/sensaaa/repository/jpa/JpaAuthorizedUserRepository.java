package sensaaa.repository.jpa;


import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import sensaaa.domain.AuthorizedUser;
import sensaaa.repository.AuthorizedUserRepository;

@Repository
@Component
public class JpaAuthorizedUserRepository implements AuthorizedUserRepository {
//    private final Log log = LogFactory.getLog(JpaAuthorizedUserRepository.class);

    @PersistenceContext
    private EntityManager em;

    public AuthorizedUser getById(Long id) {
        Query q = em.createQuery("SELECT so FROM AuthorizedUser so WHERE so.id = :id");
        q.setParameter("id", id);
        return (AuthorizedUser) q.getSingleResult();
    }

    public AuthorizedUser getByUserId(String id) {
        Query q = em.createQuery("SELECT so FROM AuthorizedUser so WHERE so.googleUserId = :id");
        q.setParameter("id", id);
        return (AuthorizedUser) q.getSingleResult();
    }

    @SuppressWarnings("unchecked")
    public List<AuthorizedUser> listAll() {
        return (List<AuthorizedUser>) em.createQuery(
                "SELECT so FROM AuthorizedUser so ORDER BY so.createdTime, so.id").getResultList();
    }

    public AuthorizedUser saveOrUpdate(AuthorizedUser user) {
        if (em.contains(user)) {
            return em.merge(user);
        } else {
            em.persist(user);
            em.refresh(user);
            return user;
        }
    }
    
    public void delete(AuthorizedUser user) {
        em.remove(user);
    }
}
