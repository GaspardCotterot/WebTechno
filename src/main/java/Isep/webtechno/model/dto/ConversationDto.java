package Isep.webtechno.model.dto;

import Isep.webtechno.model.entity.Message;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class ConversationDto {
    private Integer id;
    BasicUserDto user;
    List<Message> messages;
    Date consultedAt;
}
