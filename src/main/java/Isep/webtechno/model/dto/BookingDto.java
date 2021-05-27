package Isep.webtechno.model.dto;

import Isep.webtechno.model.entity.BookingState;
import Isep.webtechno.model.entity.House;
import lombok.Data;

import java.util.Date;

@Data
public class BookingDto {
    private Integer id;
    private BookingState state;
    private Date startDate;
    private Date endDate;
    private BasicUserDto user;
//    private List<Message> messages;
    private HouseDto house;
}
