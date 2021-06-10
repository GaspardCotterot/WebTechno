package Isep.webtechno.controller;


import Isep.webtechno.model.converter.HouseConverter;
import Isep.webtechno.model.dto.HouseDto;
import Isep.webtechno.model.entity.House;
import Isep.webtechno.model.entity.HouseConstraint;
import Isep.webtechno.model.entity.HouseService;
import Isep.webtechno.model.entity.User;
import Isep.webtechno.model.repo.HouseConstraintRepository;
import Isep.webtechno.model.repo.HouseRepository;
import Isep.webtechno.model.repo.HouseServiceRepository;
import Isep.webtechno.model.repo.UserRepository;
import Isep.webtechno.utils.GeneralService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.json.JSONException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@RestController
@RequestMapping(path = "/house")
public class HouseController {


    private final HouseRepository houseRepository;
    private final UserRepository userRepository;
    private final HouseConstraintRepository houseConstraintRepository;
    private final HouseServiceRepository houseServiceRepository;
    private final GeneralService generalService;
    private final HouseConverter houseConverter;

    public HouseController(HouseRepository houseRepository, UserRepository userRepository, HouseConstraintRepository houseConstraintRepository, HouseServiceRepository houseServiceRepository, GeneralService generalService, HouseConverter houseConverter) {
        this.houseRepository = houseRepository;
        this.userRepository = userRepository;
        this.houseConstraintRepository = houseConstraintRepository;
        this.houseServiceRepository = houseServiceRepository;
        this.generalService = generalService;
        this.houseConverter = houseConverter;
    }

    @GetMapping(path = "/{house_id}")
    public ResponseEntity<HouseDto> getHouseById(@PathVariable int house_id) {
        House house = houseRepository.findById(house_id).orElseThrow(() -> new EntityNotFoundException("No book with id " + house_id));
        return new ResponseEntity<>(houseConverter.toDto(house), HttpStatus.OK);
    }

    @PostMapping(path = "/add")
    public String addNewHouse(@RequestParam String title, @RequestParam String description) {
        User user = generalService.getUserFromContext();
        House house = new House();
        house.setTitle(title);
        house.setDescription(description);
        house.setOwner(user);

        userRepository.save(user);
        houseRepository.save(house);
        return "Saved";
    }

    @PostMapping(path = "/{houseId}")
    ResponseEntity<House> modifyHouseTitle(@RequestParam(required = false) String title,
                                           @RequestParam(required = false) String description,
                                           @RequestParam(required = false) String address,
                                           @RequestParam(required = false) String city,
                                           @RequestParam(required = false) String country,
                                           @RequestParam(required = false) Integer postalCode,
                                           @RequestParam(required = false) String constraints,
                                           @RequestParam(required = false) String services,
                                           @PathVariable int houseId) throws JSONException, JsonProcessingException {

        User user = generalService.getUserFromContext();

        List<HouseConstraint> houseConstraints = generalService.getObjectListFromJsonString(constraints, HouseConstraint.class);
        List<HouseService> houseServices = generalService.getObjectListFromJsonString(services, HouseService.class);

        House house = houseRepository.findById(houseId).orElseThrow(() -> new EntityNotFoundException("No book with id " + houseId));
        if (!user.getHouses().contains(house)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        if (title != null && !title.equals("")) house.setTitle(title);
        if (description != null) house.setDescription(description);
        if (city != null) house.setCity(city);
        if (country != null) house.setCountry(country);
        if (address != null) house.setAddress(address);
        if (postalCode != null) house.setPostalCode(postalCode);
        if (houseConstraints.size() != 0) house.setConstraints(houseConstraints);
        if (houseServices.size() != 0) house.setServices(houseServices);

        houseRepository.save(house);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(path = "/test")
    House test() {
        House house = houseRepository.findById(1).orElseThrow();
        house.getConstraints().add(houseConstraintRepository.findById(1).orElseThrow());
        house.getConstraints().add(houseConstraintRepository.findById(2).orElseThrow());
        house.getServices().add(houseServiceRepository.findById(1).orElseThrow());
        houseRepository.save(house);
        return house;
    }

//    @PostMapping(path="/{house_id}/delete")
//    public String deleteHouseById (@PathVariable int house_id) {
//
//        houseRepository.deleteById(house_id);
//        return "Done";
//    }
//
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
