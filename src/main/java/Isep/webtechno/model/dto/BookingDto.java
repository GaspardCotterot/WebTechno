package Isep.webtechno.model.dto;

import Isep.webtechno.model.entity.BookingState;
import Isep.webtechno.model.entity.House;
import Isep.webtechno.model.entity.User;
import lombok.Data;

import java.util.Date;

@Data
public class BookingDto {

    private Integer id;

    private BookingState state;

    private BasicUserDto user1; //user sending

    private BasicUserDto user2;

    private HouseDto houseWantedByUser1;

    private HouseDto houseWantedByUser2;


    private Date startDateHouse1;
    private Date endDateHouse1;
    private Date startDateHouse2;
    private Date endDateHouse2;

    private boolean hasUser1Accepted = false;
    private boolean hasUser2Accepted = false;

}
