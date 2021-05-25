package Isep.webtechno.model.dto;

import Isep.webtechno.model.entity.BookingState;
import lombok.Data;

import java.util.Date;

@Data
public class BookingDto {
    private Integer id;
    private BookingState state;
    private Date startDate;
    private Date endDate;
    private UserDto user;
//    private List<Message> messages;
//    private House house;
}
