package Isep.webtechno.model.repo;

import Isep.webtechno.model.entity.Message;
import org.springframework.data.repository.CrudRepository;

public interface MessageRepository extends CrudRepository<Message, Integer> {
}
