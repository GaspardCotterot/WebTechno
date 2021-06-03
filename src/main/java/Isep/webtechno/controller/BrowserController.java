package Isep.webtechno.controller;

import Isep.webtechno.model.entity.House;
import Isep.webtechno.model.repo.HouseRepository;
import Isep.webtechno.utils.HouseSearch;
import Isep.webtechno.utils.HouseSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(path = "/browse")
public class BrowserController {

    @Autowired
    private HouseRepository houseRepository;

    @PostMapping(path = "/search")
    public List<House> findByLocation(@ModelAttribute HouseSearch search) {
        // TODO: implement date checking
        HouseSpecification spec = new HouseSpecification(search);
        return houseRepository.findAll(spec);
    }

    private boolean checkDatePattern(String date) { // Checks if a given date respects the pattern YYYY-MM-DD
        return date.matches("^[0-9]{4}-[0-9]{2}-[0-9]{2}$");
    }

    private boolean isDateFuture(LocalDate firstDate, LocalDate secondDate) { // Checks the order of two given dates
        return secondDate.isAfter(firstDate);
    }
}
