package Isep.webtechno.model.repo;

import Isep.webtechno.model.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer> {
}
