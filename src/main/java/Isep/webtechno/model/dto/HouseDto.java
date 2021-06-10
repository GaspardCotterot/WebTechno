package Isep.webtechno.model.dto;

import Isep.webtechno.model.entity.HouseConstraint;
import Isep.webtechno.model.entity.HouseService;
import lombok.Data;

import java.util.List;

@Data
public class HouseDto {
    private Integer id;
    private String title;
    private String description = "";
    private String address = "";
    private String city = "";
    private Integer postalCode;
    private String country = "";
    private List<HouseConstraint> constraints;
    private List<HouseService> services;
    private Integer ownerId;
    private List<PictureDto> pictures;
}
