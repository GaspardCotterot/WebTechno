package Isep.webtechno.model.dto;

import lombok.Data;

import java.util.Date;

@Data
public class MessageDto {
    private Integer id;

    private boolean isFromUser;

    private String text;

    private Date date;
}
