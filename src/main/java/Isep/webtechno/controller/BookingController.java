package Isep.webtechno.controller;


import Isep.webtechno.model.converter.BookingConverter;
import Isep.webtechno.model.dto.BookingDto;
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
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/bookings")
public class BookingController {

    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    GeneralService generalService;
    @Autowired
    HouseRepository houseRepository;
    @Autowired
    BookingConverter bookingConverter;

    @GetMapping
    private ResponseEntity<List<BookingDto>> get() {
        User userFromContext = generalService.getUserFromContext();
        List<Booking> allBookings = bookingRepository.findAllByUser(userFromContext);
        allBookings = allBookings.stream().filter(booking -> booking.getState() != BookingState.ARCHIVED).collect(Collectors.toList());
        return new ResponseEntity<>(bookingConverter.toDto(allBookings), new HttpHeaders(), HttpStatus.OK);
    }

    @GetMapping(path = "/received")
    private ResponseEntity<List<BookingDto>> getReceivedBookings() {
        User userFromContext = generalService.getUserFromContext();
        List<House> allHouses = userFromContext.getHouses();
        List<BookingDto> bookings = new ArrayList<>();
        allHouses.forEach(house -> bookings.addAll(bookingConverter.toDto(house.getBookings())));
        List<BookingDto> filteredBookings = bookings.stream()
                .filter(booking -> booking.getState() == BookingState.REQUEST || booking.getState() == BookingState.ACCEPTED)
                .collect(Collectors.toList());

        return new ResponseEntity<>(filteredBookings, HttpStatus.OK);
    }

    @PostMapping(path = "/add")
    public String addNewBooking(@RequestParam Date startDate,
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
        if (!userFromContext.getBookings().contains(booking)) return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        bookingRepository.deleteById(bookingId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(path = "/change-received-booking-state/{bookingId}")
    private ResponseEntity<String> changeReceivedBookingState(@PathVariable int bookingId, @RequestParam BookingState bookingState) {
        User userFromContext = generalService.getUserFromContext();
        Booking booking = bookingRepository.findById(bookingId).orElseThrow();
        if (!booking.getHouse().getOwner().getId().equals(userFromContext.getId()))
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);

        booking.setState(bookingState);
        bookingRepository.save(booking);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(path = "/change-sent-booking-state/{bookingId}")
    private ResponseEntity<String> changeSentBookingState(@PathVariable int bookingId, @RequestParam BookingState bookingState) {
        User userFromContext = generalService.getUserFromContext();
        Booking booking = bookingRepository.findById(bookingId).orElseThrow();
        if (!userFromContext.getBookings().contains(booking)) return new ResponseEntity<>(HttpStatus.FORBIDDEN);

        booking.setState(bookingState);
        bookingRepository.save(booking);

        return new ResponseEntity<>(HttpStatus.OK);
    }

}
