package Isep.webtechno.controller;


import Isep.webtechno.model.entity.Booking;
import Isep.webtechno.model.entity.BookingState;
import Isep.webtechno.model.entity.House;
import Isep.webtechno.model.entity.User;
import Isep.webtechno.model.repo.BookingRepository;
import Isep.webtechno.model.repo.HouseRepository;
import Isep.webtechno.utils.GeneralService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(path = "/bookings")
public class BookingController {

    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    GeneralService generalService;
    @Autowired
    HouseRepository houseRepository;

    @GetMapping
    private ResponseEntity<List<Booking>> get() {
        User userFromContext = generalService.getUserFromContext();
        List<Booking> allBookings = bookingRepository.findAllByUser(userFromContext);
        return new ResponseEntity<>(allBookings, new HttpHeaders(), HttpStatus.OK);
    }

    @PostMapping(path = "/add")
    public String addNewHouse(@RequestParam Date startDate,
                              @RequestParam Date endDate,
                              @RequestParam Integer houseId,
                              @RequestParam(required = false) Integer offeredHouseId) {//todo handle offered house
        User userFromContext = generalService.getUserFromContext();
        House house = houseRepository.findById(houseId).orElseThrow();

        Booking booking = new Booking();

        booking.setState(BookingState.REQUEST);
        booking.setStartDate(startDate);
        booking.setEndDate(endDate);
        booking.setHouse(house);
        booking.setUser(userFromContext);
        bookingRepository.save(booking);
        return "Saved";
    }

    @DeleteMapping(path = "/{bookingId}")
    public ResponseEntity<String> deleteBookingById(@PathVariable int bookingId) {
        User userFromContext = generalService.getUserFromContext();
        Booking booking = bookingRepository.findById(bookingId).orElseThrow();
        if (userFromContext.getBookings().contains(booking)) return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        bookingRepository.deleteById(bookingId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
