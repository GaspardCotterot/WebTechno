package Isep.webtechno.controller;

import Isep.webtechno.model.entity.HouseService;
import Isep.webtechno.model.repo.HouseServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/house-service")
public class HouseServiceController {
    @Autowired
    HouseServiceRepository houseServiceRepository;

    @GetMapping()
    public List<HouseService> getAll() {
        return (List<HouseService>) houseServiceRepository.findAll();
    }
}
