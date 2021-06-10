package Isep.webtechno.utils;

import Isep.webtechno.model.entity.Booking;
import Isep.webtechno.model.entity.House;
import Isep.webtechno.model.repo.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookingService {
    private final BookingRepository bookingRepository;

    @Autowired
    public BookingService(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    public List<Booking> getAllBookingsFromHouse(House house) {
        List<Booking> bookingsAsUser1 = bookingRepository.findAllByUser1(house.getOwner());
        List<Booking> bookingsAsUser2 = bookingRepository.findAllByUser2(house.getOwner());

        List<Booking> allBookings = new ArrayList<>();
        allBookings.addAll(bookingsAsUser1);
        allBookings.addAll(bookingsAsUser2);
        return allBookings;
    }
}
