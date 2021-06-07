package Isep.webtechno.controller;

import Isep.webtechno.model.entity.House;
import Isep.webtechno.model.repo.HouseRepository;
import Isep.webtechno.utils.HouseSearch;
import Isep.webtechno.utils.HouseSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static Isep.webtechno.utils.BrowserService.*;

@RestController
@RequestMapping(path = "/browse")
public class BrowserController {

    @Autowired
    private HouseRepository houseRepository;

    @PostMapping(path = "/search")
    public List<House> findByLocation(@ModelAttribute HouseSearch search) {
        System.out.println(search.getLocation()+" "+search.getArrival()+" "+search.getDeparture());
        if (checkDatePattern(search.getArrival()) && checkDatePattern(search.getDeparture())) {
            if (isDateAfter(stringToLocalDate(search.getArrival()), LocalDate.now())
                    && isDateAfter(stringToLocalDate(search.getDeparture()), LocalDate.now())
                    && isDateAfter(stringToLocalDate(search.getDeparture()), stringToLocalDate(search.getArrival()))) {
                HouseSpecification spec = new HouseSpecification(search);
                return houseRepository.findAll(spec);
            }
        }
        return new ArrayList<>();
    }
}
