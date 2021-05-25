package Isep.webtechno.model.converter;

import Isep.webtechno.model.dto.BasicUserDto;
import Isep.webtechno.model.dto.UserDto;
import Isep.webtechno.model.entity.User;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class UserConverter {
    ModelMapper modelMapper = new ModelMapper();

    public UserDto toDto(User user) {
        UserDto userDto = new UserDto();
        modelMapper.map(user, userDto);

        return userDto;
    }

    public BasicUserDto toBasicDto(User user) {
        BasicUserDto userDto = new BasicUserDto();
        modelMapper.map(user, userDto);
        return userDto;
    }

}
