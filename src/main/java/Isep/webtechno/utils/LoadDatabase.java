package Isep.webtechno.utils;

import Isep.webtechno.model.entity.User;
import Isep.webtechno.model.repo.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;


@Configuration
class LoadDatabase {

    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    CommandLineRunner initDatabase(UserRepository repository, PasswordEncoder encoder) {
        User basic = new User();
        basic.setName("Basic User");
        basic.setMail("user@user.com");
        basic.setPassword(encoder.encode("password"));

        User admin = new User();
        basic.setName("Admin User");
        basic.setMail("admin@admin.com");
        basic.setPassword(encoder.encode("matthieu"));

        return args -> {
            if(repository.findByMail("user@user.com").isEmpty()) log.info("Preloading " + repository.save(basic));
            if(repository.findByMail("admin@admin.com").isEmpty()) log.info("Preloading " + repository.save(admin));
        };
    }
}