package Isep.webtechno.utils;

import Isep.webtechno.model.entity.Booking;
import Isep.webtechno.model.entity.BookingState;
import Isep.webtechno.model.entity.House;
import Isep.webtechno.model.repo.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookingService {
    private final BookingRepository bookingRepository;

    @Autowired
    public BookingService(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    public List<Booking> getAllBookingsFromHouse(House house) {
        List<Booking> bookingsAsUser1 = bookingRepository.findAllByHouseWantedByUser1(house);
        List<Booking> bookingsAsUser2 = bookingRepository.findAllByHouseWantedByUser2(house);

        List<Booking> allBookings = new ArrayList<>();
        allBookings.addAll(bookingsAsUser1);
        allBookings.addAll(bookingsAsUser2);
        allBookings = allBookings
                .stream()
                .filter(booking -> booking.getState() == BookingState.ACCEPTED)
                .collect(Collectors.toList());
        return allBookings;
    }
}
