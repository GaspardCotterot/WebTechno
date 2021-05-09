package Isep.webtechno.model.repo;

import Isep.webtechno.model.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {
    public User findByMail(String Mail);
}
