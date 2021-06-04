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

    private boolean checkDatePattern(String date) { // Checks if a given date respects the pattern YYYY-MM-DD
        return date.matches("^[0-9]{4}-[0-9]{2}-[0-9]{2}$");
    }

    private LocalDate stringToLocalDate(String date) { // Convert a String of type YYYY-MM-DD to LocalDate object
        String[] dateStrings = date.split("-");
        int[] dateIntegers = new int[3];
        try {
            dateIntegers = Arrays.stream(dateStrings).mapToInt(Integer::parseInt).toArray();
        } catch (Exception e) {
            System.out.println("Got a problem converting the string date to LocalDate object !");
            e.printStackTrace();
        }
        return LocalDate.of(dateIntegers[0], dateIntegers[1], dateIntegers[2]);
    }

    private boolean isDateAfter(LocalDate secondDate, LocalDate firstDate) { // Checks the order of two given dates
        return secondDate.isAfter(firstDate);
    }
}
