package Isep.webtechno.model.dto;

import Isep.webtechno.model.entity.House;
import Isep.webtechno.security.Role;
import lombok.Data;

import java.util.List;

@Data
public class BasicUserDto {
    private String name;
    private String mail;
    private Role role = Role.USER;
    private List<House> houses;
}
