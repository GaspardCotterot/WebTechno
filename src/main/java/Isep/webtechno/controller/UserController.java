package Isep.webtechno.controller;

import Isep.webtechno.model.entity.Booking;
import Isep.webtechno.model.entity.User;
import Isep.webtechno.model.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path="/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping
    private ResponseEntity<List<User>> getAll() {
        List<User> allUsers = new ArrayList<>();
        userRepository.findAll().forEach(allUsers::add);
        return new ResponseEntity<>(allUsers, new HttpHeaders(), HttpStatus.OK);
    }

    @PostMapping("/by-mail")
    private ResponseEntity<User> getUserByMail(@RequestParam String mail) {
        User byMail = userRepository.findByMail(mail);
        return new ResponseEntity<>(byMail, new HttpHeaders(), HttpStatus.OK);
    }

    @PostMapping(path="/add")
    public String addNewUser (@RequestParam String name, @RequestParam String mail) {
        // @ResponseBody means the returned String is the response, not a view name
        // @RequestParam means it is a parameter from the GET or POST request

        User n = new User();

        n.setName(name);
        n.setMail(mail);
        n.setPassword(passwordEncoder.encode("pass"));
        userRepository.save(n);
        return "Saved";
    }

    @PostMapping(path = "/{user-id}/add-booking")
    String addBookingToUser(@RequestParam User user,@RequestParam Booking booking) {
        user.getBookings().add(booking);
        userRepository.save(user);
        return "Changed";
    }



}
