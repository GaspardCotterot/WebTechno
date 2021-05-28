package Isep.webtechno.controller;

import Isep.webtechno.model.entity.Booking;
import Isep.webtechno.model.entity.House;
import Isep.webtechno.model.entity.User;
import Isep.webtechno.model.repo.HouseRepository;
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
@RequestMapping(path = "/admin/user")
public class AdminUserController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private HouseRepository houseRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping
    private ResponseEntity<List<User>> getAll() {
        List<User> allUsers = new ArrayList<>();
        userRepository.findAll().forEach(allUsers::add);
        return new ResponseEntity<>(allUsers, new HttpHeaders(), HttpStatus.OK);
    }

    @GetMapping(path="/all")
    public @ResponseBody Iterable<User> getAllUsers() {
        // This returns a JSON or XML with the users
        return userRepository.findAll();
    }

    @GetMapping(path = "/{user_id}")
    public User getUserById(@PathVariable int user_id) {
        return userRepository.findById(user_id).orElseThrow(() -> new UsernameNotFoundException("No user with id " + user_id));
    }

    @PostMapping
    public String addNewUser(@RequestParam String name, @RequestParam String mail, @RequestParam String password) {
        User user = new User();
        user.setName(name);
        user.setMail(mail);
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);
        return "Saved";
    }

    @DeleteMapping(path = "/delete/{user_id}")
    public String deleteUserById(@PathVariable int user_id) {
        userRepository.deleteById(user_id);
        return "Done";
    }

    //todo
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
        user.getHouses().add(house);
        userRepository.save(user);
        houseRepository.save(house);
        return "Changed";
    }

    @PostMapping(path = "/{user_id}/modify-name")
    String modifyUserName(@RequestParam String name, @PathVariable int user_id) {
        User user = getUserById(user_id);
        user.setName(name);
        userRepository.save(user);
        return "Changed";

    }

    @PostMapping(path = "/{user_id}/modify-mail")
    String modifyUserMail(@RequestParam String mail, @PathVariable int user_id) {
        User user = getUserById(user_id);
        user.setMail(mail);
        userRepository.save(user);
        return "Changed";

    }

}
