package Isep.webtechno.model.dto;

import Isep.webtechno.model.entity.House;
import Isep.webtechno.security.Role;
import lombok.Data;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class BasicUserDto {
    private String name;
    private String mail;
    private Role role = Role.USER;
    private List<House> houses;


    public JSONObject toJsonObject() throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", name);
        jsonObject.put("mail", mail);
        jsonObject.put("role", role);
        jsonObject.put("housesIds", new JSONArray(getHouses().stream().map(House::getId).collect(Collectors.toList())));
        return jsonObject;
    }
}
