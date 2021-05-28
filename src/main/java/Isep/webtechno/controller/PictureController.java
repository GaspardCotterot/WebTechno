package Isep.webtechno.controller;

import Isep.webtechno.model.entity.House;
import Isep.webtechno.model.entity.Picture;
import Isep.webtechno.model.entity.User;
import Isep.webtechno.model.repo.HouseRepository;
import Isep.webtechno.model.repo.PictureRepository;
import Isep.webtechno.utils.FileUploadUtil;
import Isep.webtechno.utils.GeneralService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.util.Objects;

@RestController
@RequestMapping(path = "/picture")
public class PictureController {
    private final PictureRepository pictureRepository;
    private final HouseRepository houseRepository;
    private final GeneralService generalService;

    @Autowired
    public PictureController(PictureRepository pictureRepository, HouseRepository houseRepository, GeneralService generalService) {
        this.pictureRepository = pictureRepository;
        this.houseRepository = houseRepository;
        this.generalService = generalService;
    }

    @PostMapping(path = "/add-or-edit/{houseId}")
    private ResponseEntity<String> addOrEdit(@PathVariable Integer houseId,
                                             @RequestParam(required = false) String url,
                                             @RequestParam(required = false) Integer index,
                                             @RequestParam(name = "picture", required = false) MultipartFile multipartFile
    ) throws IOException {
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
            picture.setHouse(house);
            index = user.getHouses().get(0).getPictures().size();
        } else {
            picture = house.getPictures().get(index);
        }

        if(multipartFile != null) {
            String fileName = StringUtils.cleanPath(Objects.requireNonNull(multipartFile.getOriginalFilename()));
            fileName = "User" + user.getId() + "-" + index + "-" + fileName;
            FileUploadUtil.saveFile(fileName, multipartFile);
            picture.setUrl(fileName);
            picture.setFromInternet(false);
        } else {
            if(url != null) picture.setUrl(url);
            picture.setFromInternet(true);
        }


        pictureRepository.save(picture);
        houseRepository.save(house);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(path = "/files/{filename:.+}", produces = MediaType.IMAGE_JPEG_VALUE)
    @ResponseBody
    public ResponseEntity<Resource> getFile(@PathVariable String filename) {
        Resource file = FileUploadUtil.load(filename);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }



}
