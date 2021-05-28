package Isep.webtechno.model.converter;

import Isep.webtechno.model.dto.HouseDto;
import Isep.webtechno.model.entity.House;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class HouseConverter {
    ModelMapper modelMapper = new ModelMapper();

    public HouseDto toDto(House house) {
        HouseDto houseDto = new HouseDto();
        modelMapper.map(house, houseDto);

        return houseDto;
    }

}
