package Isep.webtechno.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

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
}
