package Isep.webtechno.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Data
public class Conversation {
    @Id
    @GeneratedValue
    private Integer id;

    @ManyToMany()
    List<User> users = new ArrayList<>(2);

    @JsonIgnore
    @OneToMany(mappedBy = "conversation")
    List<Message> messages;

    Date lastUpdatedAt;


}
