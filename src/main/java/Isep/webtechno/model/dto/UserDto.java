package Isep.webtechno.model.dto;

import Isep.webtechno.model.entity.Booking;
import Isep.webtechno.model.entity.House;
import Isep.webtechno.security.Role;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class UserDto {
    private Integer id;
    private String name;
    private String mail;
    private Role role = Role.USER;
    private List<Booking> bookings = new ArrayList<>();
    private List<House> houses;
}
