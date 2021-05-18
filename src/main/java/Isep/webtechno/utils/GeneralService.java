package Isep.webtechno.utils;

import Isep.webtechno.model.entity.User;
import Isep.webtechno.model.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class GeneralService {
    @Autowired
    private UserRepository userRepository;

    public User getUserFromContext() {
        String mail = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByMail(mail).orElseThrow(() ->
                new UsernameNotFoundException(String.format("Mail %s not found", mail))
        );
    }
}
