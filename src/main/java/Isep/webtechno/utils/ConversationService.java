package Isep.webtechno.utils;

import Isep.webtechno.model.entity.Conversation;
import Isep.webtechno.model.entity.Message;
import Isep.webtechno.model.entity.User;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class ConversationService {
    public User getOtherUser(Conversation conversation, User user) {
        return conversation.getUsers().stream().filter(user1 -> !user1.getId().equals(user.getId())).collect(Collectors.toList()).get(0);
    }

    public boolean isMessageFromUser(Message message, User user) {
        return message.getUserSending().equals(user);
    }
}
