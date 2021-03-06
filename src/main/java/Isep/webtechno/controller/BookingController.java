package Isep.webtechno.controller;


import Isep.webtechno.model.converter.BookingConverter;
import Isep.webtechno.model.converter.HouseConverter;
import Isep.webtechno.model.dto.BookingDto;
import Isep.webtechno.model.dto.HouseBasicDto;
import Isep.webtechno.model.entity.Booking;
import Isep.webtechno.model.entity.BookingState;
import Isep.webtechno.model.entity.House;
import Isep.webtechno.model.entity.User;
import Isep.webtechno.model.repo.BookingRepository;
import Isep.webtechno.model.repo.HouseRepository;
import Isep.webtechno.utils.GeneralService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    HouseConverter houseConverter;
    @Autowired
    BookingConverter bookingConverter;

    @GetMapping
    private ResponseEntity<List<BookingDto>> get() {
        User userFromContext = generalService.getUserFromContext();
        List<Booking> allBookings = bookingRepository.findAllByUser1(userFromContext);
        allBookings = allBookings.stream().filter(booking -> booking.getState() != BookingState.ARCHIVED).collect(Collectors.toList());
        return new ResponseEntity<>(bookingConverter.toDto(allBookings), HttpStatus.OK);
    }

    @GetMapping(path = "/received")
    private ResponseEntity<List<BookingDto>> getReceivedBookings() {
        User userFromContext = generalService.getUserFromContext();
        List<Booking> bookings = bookingRepository.findAllByUser2(userFromContext);
        List<Booking> filteredBookings = bookings.stream()
                .filter(booking -> booking.getState() == BookingState.REQUEST || booking.getState() == BookingState.ACCEPTED)
                .collect(Collectors.toList());

        return new ResponseEntity<>(bookingConverter.toDto(filteredBookings), HttpStatus.OK);
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
        booking.setStartDateHouse1(startDate);
        booking.setEndDateHouse1(endDate);
        booking.setHouseWantedByUser1(house);
        booking.setUser1(userFromContext);
        booking.setUser2(house.getOwner());

        bookingRepository.save(booking);
        return "Saved";
    }

    @DeleteMapping(path = "/{bookingId}")
    public ResponseEntity<String> deleteBookingById(@PathVariable int bookingId) {
        User userFromContext = generalService.getUserFromContext();
        Booking booking = bookingRepository.findById(bookingId).orElseThrow();
        if (!booking.getUser1().equals(userFromContext)) return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        bookingRepository.deleteById(bookingId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(path = "/change-received-booking-state/{bookingId}")
    private ResponseEntity<String> changeReceivedBookingState(@PathVariable int bookingId, @RequestParam BookingState bookingState) {
        User userFromContext = generalService.getUserFromContext();
        Booking booking = bookingRepository.findById(bookingId).orElseThrow();
        if (!booking.getUser2().equals(userFromContext))
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);

        booking.setState(bookingState);
        bookingRepository.save(booking);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(path = "/change-sent-booking-state/{bookingId}")//todo simplify
    private ResponseEntity<String> changeSentBookingState(@PathVariable int bookingId, @RequestParam BookingState bookingState) {
        User userFromContext = generalService.getUserFromContext();
        Booking booking = bookingRepository.findById(bookingId).orElseThrow();
        if (!booking.getUser1().equals(userFromContext)) return new ResponseEntity<>(HttpStatus.FORBIDDEN);

        booking.setState(bookingState);
        bookingRepository.save(booking);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(path = "/get-others-houses/{bookingId}")
    private ResponseEntity<List<HouseBasicDto>> getOthersHouses(@PathVariable Integer bookingId) {
        User userFromContext = generalService.getUserFromContext();
        Booking booking = bookingRepository.findById(bookingId).orElseThrow();
        if (!booking.getUser1().equals(userFromContext) && !booking.getUser2().equals(userFromContext))
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);

        List<House> houses;
        if (booking.getUser1().equals(userFromContext)) {
            houses = booking.getUser2().getHouses();
        } else {
            houses = booking.getUser1().getHouses();
        }
        return new ResponseEntity<>(houseConverter.toBasicDto(houses), HttpStatus.OK);
    }

    @PostMapping(path = "/{bookingId}")
    private ResponseEntity<String> editBooking(@PathVariable Integer bookingId,
                                               @RequestParam(required = false) Date startDate,
                                               @RequestParam(required = false) Date endDate,
                                               @RequestParam(required = false) Integer houseId) {

        User userFromContext = generalService.getUserFromContext();
        Booking booking = bookingRepository.findById(bookingId).orElseThrow();
        if (!booking.getUser1().equals(userFromContext) && !booking.getUser2().equals(userFromContext))
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);


        if (booking.getUser1().equals(userFromContext)) {
            booking.setStartDateHouse1(startDate);
            booking.setEndDateHouse1(endDate);
            if (houseId != null) {
                House house = houseRepository.findById(houseId).orElseThrow();
                booking.setHouseWantedByUser1(house);
            }
            booking.setHasUser2Accepted(false);
        } else {
            booking.setStartDateHouse2(startDate);
            booking.setEndDateHouse2(endDate);
            if (houseId != null) {
                House house = houseRepository.findById(houseId).orElseThrow();
                booking.setHouseWantedByUser2(house);
            }
            booking.setHasUser1Accepted(false);
        }

        bookingRepository.save(booking);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping(path = "{bookingId}")
    private ResponseEntity<String> acceptBooking(@PathVariable Integer bookingId) {
        User userFromContext = generalService.getUserFromContext();
        Booking booking = bookingRepository.findById(bookingId).orElseThrow();

        if (!booking.getStartDateHouse1().equals(booking.getStartDateHouse2()))
            return new ResponseEntity<>("Starting dates does not match", HttpStatus.BAD_REQUEST);
        if (!booking.getEndDateHouse1().equals(booking.getEndDateHouse2()))
            return new ResponseEntity<>("Ending dates does not match", HttpStatus.BAD_REQUEST);

        if (booking.getUser1().equals(userFromContext)) {
            if (booking.getHouseWantedByUser2() == null)
                return new ResponseEntity<>("No house selected", HttpStatus.BAD_REQUEST);
            booking.setHasUser1Accepted(true);
        } else if (booking.getUser2().equals(userFromContext)) {
            if (booking.getHouseWantedByUser1() == null)
                return new ResponseEntity<>("No house selected", HttpStatus.BAD_REQUEST);
            booking.setHasUser2Accepted(true);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        if (booking.isHasUser1Accepted() && booking.isHasUser2Accepted()) {
            booking.setState(BookingState.ACCEPTED);
        }

        bookingRepository.save(booking);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
