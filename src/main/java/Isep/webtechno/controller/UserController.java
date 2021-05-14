package Isep.webtechno.controller;

import Isep.webtechno.model.entity.Booking;
import Isep.webtechno.model.entity.House;
import Isep.webtechno.model.entity.User;
import Isep.webtechno.model.repo.UserRepository;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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

    @GetMapping(path = "/get_by_id/{user_id}")
    public User getUserById(@PathVariable int user_id){

        if (userRepository.findById(user_id).isPresent()) {
            return userRepository.findById(user_id).get();
        }
        return null;
    }

    @PostMapping("/by-mail")
    private ResponseEntity<User> getUserByMail(@RequestParam String mail) {
        User byMail = userRepository.findByMail(mail).orElseThrow(() ->
                new UsernameNotFoundException(String.format("Mail %s not found", mail))
        );
        return new ResponseEntity<>(byMail, new HttpHeaders(), HttpStatus.OK);
    }

    @PostMapping("/get-user-info")
    private ResponseEntity<String> getUserInfoUsingToken() throws JSONException {
        String mail = SecurityContextHolder.getContext().getAuthentication().getName();
        User byMail = userRepository.findByMail(mail).orElseThrow(() ->
                new UsernameNotFoundException(String.format("Mail %s not found", mail))
        );
        return new ResponseEntity<>(byMail.getBasicInfos(), new HttpHeaders(), HttpStatus.OK);
    }

    @PostMapping(path="/add")
    public String addNewUser (@RequestParam String name, @RequestParam String mail, @RequestParam String password) {
        // @ResponseBody means the returned String is the response, not a view name
        // @RequestParam means it is a parameter from the GET or POST request

        User user = new User();

        user.setName(name);
        user.setMail(mail);
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);
        return "Saved";
    }

    @PostMapping(path="/{user_id}/delete")
    public String deleteUserById (@PathVariable int user_id) {

        userRepository.deleteById(user_id);
        return "Done";
    }


    @PostMapping(path = "/{user_id}/add-booking")
    String addBookingToUser(@RequestBody Booking booking, @PathVariable int user_id) {

        User user = getUserById(user_id);
        if (user != null) {
            user.getBookings().add(booking);
            userRepository.save(user);
            return "Changed";
        } else {
            return "Error, no user with this id";
        }
    }

    @PostMapping(path = "/{user_id}/add-house")
    String addHouseToUser(@RequestBody House house, @PathVariable int user_id) {

        User user = getUserById(user_id);
        if (user != null) {
            user.getHouses().add(house);
            userRepository.save(user);
            return "Changed";
        } else {
            return "Error, no user with this id";
        }
    }

    @PostMapping(path = "/{user_id}/modify-name")
    String modifyUserName(@RequestParam String name, @PathVariable int user_id) {

        User user = getUserById(user_id);
        if (user != null) {
            user.setName(name);
            userRepository.save(user);
            return "Changed";
        } else {
            return "Error, no user with this id";
        }
    }

    @PostMapping(path = "/{user_id}/modify-mail")
    String modifyUserMail(@RequestParam String mail, @PathVariable int user_id) {

        User user = getUserById(user_id);
        if (user != null) {
            user.setMail(mail);
            userRepository.save(user);
            return "Changed";
        } else {
            return "Error, no user with this id";
        }
    }

}
