package Isep.webtechno.model.converter;

import Isep.webtechno.model.dto.BookingDto;
import Isep.webtechno.model.entity.Booking;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class BookingConverter {

    @Autowired
    UserConverter userConverter;

    @Autowired
    HouseConverter houseConverter;

    public BookingDto toDto(Booking booking) {
        BookingDto bookingDto = new BookingDto();
        bookingDto.setId(booking.getId());
        bookingDto.setState(booking.getState());

        bookingDto.setStartDateHouse1(booking.getStartDateHouse1());
        bookingDto.setEndDateHouse1(booking.getEndDateHouse1());
        bookingDto.setUser1(userConverter.toBasicDto(booking.getUser1()));
        bookingDto.setHouseWantedByUser1(houseConverter.toBasicDto(booking.getHouseWantedByUser1()));

        bookingDto.setStartDateHouse2(booking.getStartDateHouse2());
        bookingDto.setEndDateHouse2(booking.getEndDateHouse2());
        bookingDto.setUser2(userConverter.toBasicDto(booking.getUser2()));
        if (booking.getHouseWantedByUser2() != null)
            bookingDto.setHouseWantedByUser2(houseConverter.toBasicDto(booking.getHouseWantedByUser2()));

        bookingDto.setHasUser1Accepted(booking.isHasUser1Accepted());
        bookingDto.setHasUser2Accepted(booking.isHasUser2Accepted());

        return bookingDto;
    }

    public List<BookingDto> toDto(List<Booking> bookings) {
        return bookings.stream().map(this::toDto).collect(Collectors.toList());
    }


}
