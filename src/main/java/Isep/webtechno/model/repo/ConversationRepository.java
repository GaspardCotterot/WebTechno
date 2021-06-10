package Isep.webtechno.model.repo;

import Isep.webtechno.model.entity.Conversation;
import org.springframework.data.repository.CrudRepository;

public interface ConversationRepository extends CrudRepository<Conversation, Integer> {
}
