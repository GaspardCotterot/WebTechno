package Isep.webtechno.utils;

import Isep.webtechno.model.entity.User;
import Isep.webtechno.model.entity.enums.HouseConstraint;
import Isep.webtechno.model.entity.enums.HouseService;
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
import java.util.stream.Collectors;


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

        List<HouseConstraint> constraints = new ArrayList<>();
        constraints.add(new HouseConstraint("No smoking", " No smoking in the accommodation"));
        constraints.add(new HouseConstraint("No noise at nighttime", "No noise after 23h"));
        constraints.add(new HouseConstraint("2 children / accommodation", "2 children maximum per accommodation"));
        constraints.add(new HouseConstraint("No children", "No children"));
        constraints.add(new HouseConstraint("No Pets", "No pets allowed"));

        List<HouseService> services = new ArrayList<>();
        services.add(new HouseService("Keep pets", "empty desc"));
        services.add(new HouseService("Watering plants", "empty desc"));
        services.add(new HouseService("Clean the house", "empty desc"));

        return args -> {
            if(repository.findByMail("user@user.com").isEmpty()) log.info("Preloading basic user " + repository.save(basic));
            if(repository.findByMail("admin@admin.com").isEmpty()) log.info("Preloading admin user " + repository.save(admin));
            if(!houseConstraintRepository.findAll().iterator().hasNext()) log.info("Preloading constraints " + houseConstraintRepository.saveAll(constraints));
            if(!houseServiceRepository.findAll().iterator().hasNext()) log.info("Preloading services " + houseServiceRepository.saveAll(services));
        };
    }
}