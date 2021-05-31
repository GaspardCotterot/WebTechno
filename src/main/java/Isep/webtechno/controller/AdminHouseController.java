package Isep.webtechno.controller;


import Isep.webtechno.model.converter.HouseConverter;
import Isep.webtechno.model.dto.HouseDto;
import Isep.webtechno.model.entity.House;
import Isep.webtechno.model.entity.User;
import Isep.webtechno.model.repo.HouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path="/admin/house")
public class AdminHouseController {

    @Autowired
    HouseConverter converter;

    @Autowired
    private HouseRepository houseRepository;

    @GetMapping
    private ResponseEntity<List<House>> getAll() {
        List<House> allHouses = new ArrayList<>();
        houseRepository.findAll().forEach(allHouses::add);
        return new ResponseEntity<>(allHouses, new HttpHeaders(), HttpStatus.OK);
    }

    @GetMapping(path="/all")
    public @ResponseBody Iterable<House> getAllHouses() {
        // This returns a JSON or XML with the users
        return houseRepository.findAll();
    }

    @GetMapping(path = "/{house_id}")
    public House getHouseById(@PathVariable int house_id){
        return houseRepository.findById(house_id).orElseThrow(() -> new EntityNotFoundException("No book with id " + house_id));
    }

    /* public @ResponseBody Iterable<HouseDto> getAllHouses() {
        // This returns a JSON or XML with the users
        List<House> allHouses = (List<House>) houseRepository.findAll();
        List<HouseDto> allHousesDto = allHouses.stream().map(house -> converter.toDto(house)).collect(Collectors.toList());
        return allHousesDto;
    }*/


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

    @DeleteMapping(path="/delete/{house_id}")
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
