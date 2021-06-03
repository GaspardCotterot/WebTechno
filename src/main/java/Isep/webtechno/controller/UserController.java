package Isep.webtechno.controller;

import Isep.webtechno.model.converter.UserConverter;
import Isep.webtechno.model.dto.BasicUserDto;
import Isep.webtechno.model.entity.House;
import Isep.webtechno.model.entity.User;
import Isep.webtechno.utils.GeneralService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/user")
public class UserController {

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



}
