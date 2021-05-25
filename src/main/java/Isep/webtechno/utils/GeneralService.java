package Isep.webtechno.utils;

import Isep.webtechno.model.entity.House;
import Isep.webtechno.model.entity.User;
import Isep.webtechno.model.repo.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class GeneralService {
    @Autowired
    private UserRepository userRepository;

    public User getUserFromContext() {
        String mail = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByMail(mail).orElseThrow(() ->
                new UsernameNotFoundException(String.format("Mail %s not found", mail))
        );
    }

    public <T> List<T> getObjectListFromJsonString(String json, Class<T> tClass) throws JSONException, JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        JSONArray jsonArray = new JSONArray(json);
        List<T> objectList = new ArrayList<>();
        for(int i=0; i<jsonArray.length(); i++) {
            objectList.add(mapper.readValue(jsonArray.get(i).toString(), tClass));
        }
        return objectList;
    }

    public JSONObject getBasicInfosFromUser(User user) throws JSONException {
        return (new  JSONObject())
                .put("mail", user.getMail())
                .put("name", user.getName())
                .put("role", user.getRole())
                .put("housesIds", new JSONArray(user.getHouses().stream().map(House::getId).collect(Collectors.toList())))
                ;
    }

}
