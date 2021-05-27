package Isep.webtechno.controller;

import Isep.webtechno.model.converter.UserConverter;
import Isep.webtechno.model.dto.BasicUserDto;
import Isep.webtechno.model.dto.UserDto;
import Isep.webtechno.model.entity.User;
import Isep.webtechno.model.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @Autowired
    UserRepository userRepository;
    @Autowired
    UserConverter userConverter;
    @GetMapping(path = "/")
    private String hello() {
        System.out.println("Hello");
        return "Hello";
    }

    @PostMapping(path = "/")
    private String helloPost() {
        System.out.println("Hello POST");
        return "Hello (POST)";
    }

    @GetMapping(path = "/admin")
    private String admin() {
        return "Welcome admin";
    }

    @GetMapping(path = "/auth")
    private String connected() {
        return "You are connected";
    }

    @PostMapping(path = "/auth")
    private String connectedPost() {
        return "You are connected (POST)";
    }

    @PostMapping(path = "/free-access/userbasic")
    private UserDto testUserBasic() {
        User user = userRepository.findByMail("user@user.com").orElseThrow();
        return userConverter.toDto(user);
    }
}
