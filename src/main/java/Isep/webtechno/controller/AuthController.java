package Isep.webtechno.controller;

import Isep.webtechno.model.entity.User;
import Isep.webtechno.model.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
public class AuthController {
    final UserRepository userRepository;
    final PasswordEncoder passwordEncoder;

    public AuthController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping(path = "/signin")
    private ResponseEntity<String> signin(
            @RequestParam String name,
            @RequestParam String password,
            @RequestParam String mail
    ) {
        if(userRepository.findByMail(mail).isPresent()) return new ResponseEntity<>("A user with this mail already exists.", HttpStatus.CONFLICT);

        User user = new User();
        user.setMail(mail);
        user.setPassword(passwordEncoder.encode(password));
        user.setName(name);

        userRepository.save(user);

        return new ResponseEntity<>("User added", HttpStatus.OK);
    }

}
