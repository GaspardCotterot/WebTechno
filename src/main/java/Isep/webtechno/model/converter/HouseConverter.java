package Isep.webtechno.model.converter;

import Isep.webtechno.model.dto.HouseBasicDto;
import Isep.webtechno.model.dto.HouseDto;
import Isep.webtechno.model.entity.House;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class HouseConverter {
    ModelMapper modelMapper = new ModelMapper();

    public HouseBasicDto toBasicDto(House house) {
        HouseBasicDto houseDto = new HouseBasicDto();
        modelMapper.map(house, houseDto);

        return houseDto;
    }

    public List<HouseBasicDto> toBasicDto(List<House> houses) {
        return houses.stream().map(this::toBasicDto).collect(Collectors.toList());
    }

    public HouseDto toDto(House house) {
        HouseDto houseDto = new HouseDto();

        houseDto.setTitle(house.getTitle());
        houseDto.setId(house.getId());
        houseDto.setAddress(house.getAddress());
        houseDto.setCity(house.getCity());
        houseDto.setConstraints(house.getConstraints());
        houseDto.setServices(house.getServices());
        houseDto.setCountry(house.getCountry());
        houseDto.setPostalCode(house.getPostalCode());
        houseDto.setDescription(house.getDescription());
        houseDto.setOwnerId(house.getOwner().getId());

        return houseDto;
    }

    public List<HouseDto> toDto(List<House> houses) {
        return houses.stream().map(this::toDto).collect(Collectors.toList());
    }



}
