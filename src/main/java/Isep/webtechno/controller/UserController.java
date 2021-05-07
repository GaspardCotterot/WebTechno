package Isep.webtechno.controller;

import Isep.webtechno.model.entity.Booking;
import Isep.webtechno.model.entity.User;
import Isep.webtechno.model.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping(path="/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping(path="/add")
    public @ResponseBody
    String addNewUser (@RequestParam String name
            , @RequestParam String mail) {
        // @ResponseBody means the returned String is the response, not a view name
        // @RequestParam means it is a parameter from the GET or POST request

        User n = new User();

        n.setName(name);
        n.setMail(mail);
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
