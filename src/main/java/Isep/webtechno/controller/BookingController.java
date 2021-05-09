package Isep.webtechno.controller;


import Isep.webtechno.model.entity.Booking;
import Isep.webtechno.model.entity.House;
import Isep.webtechno.model.entity.User;
import Isep.webtechno.model.repo.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path="/booking")
public class BookingController {

    @Autowired
    private BookingRepository bookingRepository;

    @GetMapping
    private ResponseEntity getAll() {
        List<Booking> allBookings = new ArrayList<>();
        bookingRepository.findAll().forEach(allBookings::add);
        return new ResponseEntity<>(allBookings, new HttpHeaders(), HttpStatus.OK);
    }

    @GetMapping(path = "/get_by_id/{booking_id}")
    public Booking getBookingById(@PathVariable int booking_id){

        if (bookingRepository.findById(booking_id).isPresent()) {
            return bookingRepository.findById(booking_id).get();
        }
        return null;
    }

    @PostMapping(path="/add")
    public String addNewHouse (@RequestParam Integer state, @RequestBody House house, User user) {
        // @ResponseBody means the returned String is the response, not a view name
        // @RequestParam means it is a parameter from the GET or POST request

        Booking booking = new Booking();

        booking.setState(state);
        booking.setHouse(house);
        booking.setUser(user);
        bookingRepository.save(booking);
        return "Saved";
    }

    @PostMapping(path="/{booking_id}/delete")
    public String deleteBookingById (@PathVariable int booking_id) {

        bookingRepository.deleteById(booking_id);
        return "Done";
    }


}
