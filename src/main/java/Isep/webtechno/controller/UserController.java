package Isep.webtechno.controller;

import Isep.webtechno.model.converter.UserConverter;
import Isep.webtechno.model.dto.BasicUserDto;
import Isep.webtechno.model.entity.House;
import Isep.webtechno.model.entity.User;
import Isep.webtechno.model.repo.UserRepository;
import Isep.webtechno.utils.GeneralService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private GeneralService generalService;
    @Autowired
    private UserConverter userConverter;

//    @GetMapping()//todo fix
    private User getUser() {
        return generalService.getUserFromContext();
    }

    @GetMapping("/get-user-info")
    private BasicUserDto getUserInfoUsingToken() {
        return userConverter.toBasicDto(getUser());
    }

    @GetMapping(path = "/get-user-houses")
    List<House> getUserHouses() {
        return getUser().getHouses();
    }


    @PostMapping(path = "/modify-profile")
    String modifyUserMail(@RequestParam String name, @RequestParam String mail) {
        System.out.println("\n!\n!\n!\n!\n!\n!\n!\n!\n!\n!\n!\n!\n!\n!\n!\n!\n!\n!\n!\n!\n!\n!" + name + " " + mail);
        User user = getUser();
        user.setName(name);
        user.setMail(mail);
        userRepository.save(user);
        return "Changed";
    }

    //todo
    //    @PostMapping(path = "/{user_id}/add-booking")
    //    String addBookingToUser(@RequestBody Booking booking, @PathVariable int user_id) {
    //        User user = getUserById(user_id);
    //        if (user != null) {
    //            user.getBookings().add(booking);
    //            userRepository.save(user);
    //            return "Changed";
    //        } else {
    //            return "Error, no user with this id";
    //        }
    //    }


    //    @PostMapping(path = "/{user_id}/add-house")
    //    String addHouseToUser(@RequestBody House house, @PathVariable int user_id) {
    //        User user = getUserById(user_id);
    //        user.getHouses().add(house);
    //        userRepository.save(user);
    //        houseRepository.save(house);
    //        return "Changed";
    //    }
    //
    //    @PostMapping(path = "/{user_id}/modify-name")
    //    String modifyUserName(@RequestParam String name, @PathVariable int user_id) {
    //        User user = getUserById(user_id);
    //        user.setName(name);
    //        userRepository.save(user);
    //        return "Changed";
    //    }
    //
    //    @PostMapping(path = "/{user_id}/modify-mail")
    //    String modifyUserMail(@RequestParam String mail, @PathVariable int user_id) {
    //        User user = getUserById(user_id);
    //        user.setMail(mail);
    //        userRepository.save(user);
    //        return "Changed";
    //    }


}
