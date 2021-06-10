package Isep.webtechno.model.converter;

import Isep.webtechno.model.dto.ConversationDto;
import Isep.webtechno.model.entity.Conversation;
import Isep.webtechno.model.entity.User;
import Isep.webtechno.utils.ConversationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ConversationConverter {

    private final UserConverter userConverter;
    private final ConversationService conversationService;

    @Autowired
    public ConversationConverter(UserConverter userConverter, ConversationService conversationService) {
        this.userConverter = userConverter;
        this.conversationService = conversationService;
    }

    public ConversationDto toDto(Conversation conversation, User user) {
        ConversationDto conversationDto = new ConversationDto();
        conversationDto.setMessages(conversation.getMessages());
        conversationDto.setId(conversation.getId());

        User otherUser = conversationService.getOtherUser(conversation, user);

        conversationDto.setUser(userConverter.toBasicDto(otherUser));
        conversationDto.setLastConsultedAt(conversation.getLastUpdatedAt());

        return conversationDto;
    }

    public List<ConversationDto> toDto(List<Conversation> conversations, User user) {
        return conversations.stream().map(conversation -> this.toDto(conversation, user)).collect(Collectors.toList());
    }
}
