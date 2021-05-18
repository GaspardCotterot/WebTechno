package Isep.webtechno.controller;


import Isep.webtechno.model.entity.House;
import Isep.webtechno.model.entity.User;
import Isep.webtechno.model.repo.HouseRepository;
import Isep.webtechno.model.repo.UserRepository;
import Isep.webtechno.utils.GeneralService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path="/house")
public class HouseController {

    @Autowired
    private HouseRepository houseRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private GeneralService generalService;

    @GetMapping(path = "/{house_id}")
    public ResponseEntity<House> getHouseById(@PathVariable int house_id){
        User user = generalService.getUserFromContext();
        House house = houseRepository.findById(house_id).orElseThrow(() -> new EntityNotFoundException("No book with id " + house_id));
        if(user.getHouses().contains(house)) {
            return new ResponseEntity<>(house, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @PostMapping(path="/add")
    public String addNewHouse (@RequestParam String title, @RequestParam String description) {
        User user = generalService.getUserFromContext();
        House house = new House();
        house.setTitle(title);
        house.setDescription(description);
        house.setOwner(user);

        userRepository.save(user);
        houseRepository.save(house);
        return "Saved";
    }

//    @PostMapping(path="/{house_id}/delete")
//    public String deleteHouseById (@PathVariable int house_id) {
//
//        houseRepository.deleteById(house_id);
//        return "Done";
//    }
//
//    @PostMapping(path = "/{house_id}/modify-title")
//    String modifyHouseTitle(@RequestParam String title, @PathVariable int house_id) {
//
//        House house = getHouseById(house_id);
//        if (house != null) {
//            house.setTitle(title);
//            houseRepository.save(house);
//            return "Changed";
//        } else {
//            return "Error, no user with this id";
//        }
//    }
//
//    @PostMapping(path = "/{house_id}/modify-description")
//    String modifyHouseDescription(@RequestParam String description, @PathVariable int house_id) {
//
//        House house = getHouseById(house_id);
//        if (house != null) {
//            house.setDescription(description);
//            houseRepository.save(house);
//            return "Changed";
//        } else {
//            return "Error, no user with this id";
//        }
//    }
//
//    @PostMapping(path = "/{house_id}/modify-owner")
//    String modifyHouseOwner(@RequestBody User owner, @PathVariable int house_id) {
//
//        House house = getHouseById(house_id);
//        if (house != null) {
//            house.setOwner(owner);
//            houseRepository.save(house);
//            return "Changed";
//        } else {
//            return "Error, no user with this id";
//        }
//    }

}
