package Isep.webtechno.model.converter;

import Isep.webtechno.model.dto.MessageDto;
import Isep.webtechno.model.entity.Message;
import Isep.webtechno.model.entity.User;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class MessageConverter {

    public MessageDto toDto(Message message, User user) {
        MessageDto messageDto = new MessageDto();
        messageDto.setDate(message.getDate());
        messageDto.setText(message.getText());
        messageDto.setId(messageDto.getId());
        messageDto.setFromUser(message.getUserSending().equals(user));

        return messageDto;
    }

    public List<MessageDto> toDto(List<Message> messages, User user) {
        return messages.stream().map(message -> this.toDto(message, user)).sorted(Comparator.comparing(MessageDto::getDate)).collect(Collectors.toList());
    }

}
