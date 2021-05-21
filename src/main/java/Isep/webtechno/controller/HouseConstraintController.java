package Isep.webtechno.controller;

import Isep.webtechno.model.entity.HouseConstraint;
import Isep.webtechno.model.repo.HouseConstraintRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/house-constraint")
public class HouseConstraintController {
    @Autowired
    HouseConstraintRepository houseConstraintRepository;

    @GetMapping()
    public List<HouseConstraint> getAll() {
        return (List<HouseConstraint>) houseConstraintRepository.findAll();
    }
}
