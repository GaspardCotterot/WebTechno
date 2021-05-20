package Isep.webtechno.controller;

import Isep.webtechno.model.repo.HouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
public class BrowserController {

    @Autowired
    private HouseRepository houseRepository;


    private boolean checkDatePattern(String date) { // Checks if a given date respects the pattern YYYY-MM-DD
        return date.matches("^[0-9]{4}-[0-9]{2}-[0-9]{2}$");
    }

    private boolean isDateFuture(LocalDate firstDate, LocalDate secondDate) { // Checks the order of two given dates
        return secondDate.isAfter(firstDate);
    }
}
