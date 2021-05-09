package Isep.webtechno.controller;


import Isep.webtechno.model.entity.House;
import Isep.webtechno.model.entity.User;
import Isep.webtechno.model.repo.HouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path="/house")
public class HouseController {

    @Autowired
    private HouseRepository houseRepository;

    @GetMapping
    private ResponseEntity<List<House>> getAll() {
        List<House> allHouses = new ArrayList<>();
        houseRepository.findAll().forEach(allHouses::add);
        return new ResponseEntity<>(allHouses, new HttpHeaders(), HttpStatus.OK);
    }

    @GetMapping(path = "/get_by_id/{house_id}")
    public House getHouseById(@PathVariable int house_id){

        if (houseRepository.findById(house_id).isPresent()) {
            return houseRepository.findById(house_id).get();
        }
        return null;
    }


    @PostMapping(path="/add")
    public String addNewHouse (@RequestParam String title, @RequestParam String description) {
        // @ResponseBody means the returned String is the response, not a view name
        // @RequestParam means it is a parameter from the GET or POST request

        House house = new House();

        house.setTitle(title);
        house.setDescription(description);
        houseRepository.save(house);
        return "Saved";
    }

    @PostMapping(path="/{house_id}/delete")
    public String deleteHouseById (@PathVariable int house_id) {

        houseRepository.deleteById(house_id);
        return "Done";
    }

    @PostMapping(path = "/{house_id}/modify-title")
    String modifyHouseTitle(@RequestParam String title, @PathVariable int house_id) {

        House house = getHouseById(house_id);
        if (house != null) {
            house.setTitle(title);
            houseRepository.save(house);
            return "Changed";
        } else {
            return "Error, no user with this id";
        }
    }

    @PostMapping(path = "/{house_id}/modify-description")
    String modifyHouseDescription(@RequestParam String description, @PathVariable int house_id) {

        House house = getHouseById(house_id);
        if (house != null) {
            house.setDescription(description);
            houseRepository.save(house);
            return "Changed";
        } else {
            return "Error, no user with this id";
        }
    }

    @PostMapping(path = "/{house_id}/modify-owner")
    String modifyHouseOwner(@RequestBody User owner, @PathVariable int house_id) {

        House house = getHouseById(house_id);
        if (house != null) {
            house.setOwner(owner);
            houseRepository.save(house);
            return "Changed";
        } else {
            return "Error, no user with this id";
        }
    }

}
