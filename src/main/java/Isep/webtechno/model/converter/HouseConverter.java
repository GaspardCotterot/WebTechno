package Isep.webtechno.model.converter;

import Isep.webtechno.model.dto.BookingDto;
import Isep.webtechno.model.dto.HouseDto;
import Isep.webtechno.model.entity.Booking;
import Isep.webtechno.model.entity.House;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class HouseConverter {
    ModelMapper modelMapper = new ModelMapper();

    public HouseDto toDto(House house) {
        HouseDto houseDto = new HouseDto();
        modelMapper.map(house, houseDto);

        return houseDto;
    }

    public List<HouseDto> toDto(List<House> houses) {
        return houses.stream().map(this::toDto).collect(Collectors.toList());
    }

}
