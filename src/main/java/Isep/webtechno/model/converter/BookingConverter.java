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

    public BookingDto toDto(Booking booking) {
        BookingDto bookingDto = new BookingDto();
        bookingDto.setId(booking.getId());
        bookingDto.setState(booking.getState());
        bookingDto.setStartDate(booking.getStartDate());
        bookingDto.setEndDate(booking.getEndDate());
        bookingDto.setUser(userConverter.toDto(booking.getUser()));

        return bookingDto;
    }

    public List<BookingDto> toDto(List<Booking> bookings) {
        return bookings.stream().map(this::toDto).collect(Collectors.toList());
    }


}
