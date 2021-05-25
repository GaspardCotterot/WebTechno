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

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path="/bookings")
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

//    @GetMapping(path = "/get_by_id/{booking_id}")
//    public Booking getBookingById(@PathVariable int booking_id){
//
//        if (bookingRepository.findById(booking_id).isPresent()) {
//            return bookingRepository.findById(booking_id).get();
//        }
//        return null;
//    }

    @PostMapping(path="/add")
    public String addNewHouse (@RequestParam Integer state, @RequestBody Integer houseId) {
        User userFromContext = generalService.getUserFromContext();
        House house = houseRepository.findById(houseId).orElseThrow();

        Booking booking = new Booking();

        booking.setState(BookingState.values()[state]);
        booking.setHouse(house);
        booking.setUser(userFromContext);
        bookingRepository.save(booking);
        return "Saved";
    }

    @DeleteMapping(path="/{bookingId}")
    public ResponseEntity<String> deleteBookingById (@PathVariable int bookingId) {
        User userFromContext = generalService.getUserFromContext();
        Booking booking = bookingRepository.findById(bookingId).orElseThrow();
        if(userFromContext.getBookings().contains(booking)) return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        bookingRepository.deleteById(bookingId);
        return new  ResponseEntity<>(HttpStatus.OK);
    }

}
