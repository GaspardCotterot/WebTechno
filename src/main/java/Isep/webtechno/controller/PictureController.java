package Isep.webtechno.controller;

import Isep.webtechno.model.entity.House;
import Isep.webtechno.model.entity.Picture;
import Isep.webtechno.model.entity.User;
import Isep.webtechno.model.repo.HouseRepository;
import Isep.webtechno.model.repo.PictureRepository;
import Isep.webtechno.utils.GeneralService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;

@RestController
@RequestMapping(path = "/picture")
public class PictureController {
    @Autowired
    PictureRepository pictureRepository;
    @Autowired
    HouseRepository houseRepository;
    @Autowired
    GeneralService generalService;

    @PostMapping(path = "/add-or-edit/{houseId}")
    private ResponseEntity<String> addOrEdit(@PathVariable Integer houseId,
                                             @RequestParam(required = false) boolean isFromInternet,
                                             @RequestParam() String url,
                                             @RequestParam(required = false) Integer index
    ) {
        User user = generalService.getUserFromContext();
        House house = houseRepository.findById(houseId).orElseThrow(() -> new EntityNotFoundException("No book with id " + houseId));
        if (!user.getHouses().contains(house)) return new ResponseEntity<>(HttpStatus.FORBIDDEN);

        if (index == null && house.getPictures().size() >= 3)
            return new ResponseEntity<>("Cannot add another picture (max is 3)", HttpStatus.BAD_REQUEST);
        if (index != null && house.getPictures().size() <= index)
            return new ResponseEntity<>("Cannot get picture (index out of bound)", HttpStatus.BAD_REQUEST);

        Picture picture;
        if (index == null) {
            picture = new Picture();
            picture.setUrl(url);
            if (isFromInternet) picture.setFromInternet(true);
            picture.setHouse(house);
        } else {
            picture = house.getPictures().get(index);
            picture.setUrl(url);
            if (isFromInternet) picture.setFromInternet(true);
        }

        pictureRepository.save(picture);
        houseRepository.save(house);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
