package Isep.webtechno.utils;

import Isep.webtechno.model.entity.User;
import Isep.webtechno.model.entity.HouseConstraint;
import Isep.webtechno.model.entity.HouseService;
import Isep.webtechno.model.repo.HouseConstraintRepository;
import Isep.webtechno.model.repo.HouseServiceRepository;
import Isep.webtechno.model.repo.UserRepository;
import Isep.webtechno.security.Role;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;


@Configuration
class LoadDatabase {

    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    CommandLineRunner initDatabase(UserRepository repository, HouseConstraintRepository houseConstraintRepository, HouseServiceRepository houseServiceRepository, PasswordEncoder encoder) {
        User basic = new User();
        basic.setName("Basic User");
        basic.setMail("user@user.com");
        basic.setPassword(encoder.encode("password"));

        User admin = new User();
        admin.setName("Admin User");
        admin.setMail("admin@admin.com");
        admin.setRole(Role.ADMIN);
        admin.setPassword(encoder.encode("matthieu"));

        List<HouseService> services = new ArrayList<>();
        if (!houseServiceRepository.findAll().iterator().hasNext()) {
            services.add(new HouseService(1, "Keep pets", "empty desc"));
            services.add(new HouseService(2, "Watering plants", "empty desc"));
            services.add(new HouseService(3, "Clean the house", "empty desc"));
        }

        List<HouseConstraint> constraints = new ArrayList<>();
        if (!houseConstraintRepository.findAll().iterator().hasNext()) {
            constraints.add(new HouseConstraint(1, "No smoking", " No smoking in the accommodation"));
            constraints.add(new HouseConstraint(2, "No noise at nighttime", "No noise after 23h"));
            constraints.add(new HouseConstraint(3, "2 children / accommodation", "2 children maximum per accommodation"));
            constraints.add(new HouseConstraint(4, "No children", "No children"));
            constraints.add(new HouseConstraint(5, "No Pets", "No pets allowed"));
        }


        return args -> {
            if (repository.findByMail("user@user.com").isEmpty())
                log.info("Preloading basic user " + repository.save(basic));
            if (repository.findByMail("admin@admin.com").isEmpty())
                log.info("Preloading admin user " + repository.save(admin));
            log.info("Preloading services " + houseServiceRepository.saveAll(services));
            log.info("Preloading constraints " + houseConstraintRepository.saveAll(constraints));

        };
    }
}