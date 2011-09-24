package sensaaa.repository;


import java.util.List;

import sensaaa.domain.AuthorizedUser;

public interface AuthorizedUserRepository {

    public AuthorizedUser getById(Long id);

    public AuthorizedUser getByUserId(String userId);
    
    public List<AuthorizedUser> listAll();

    public AuthorizedUser saveOrUpdate(AuthorizedUser so);
    
    public void delete(AuthorizedUser so);
}
