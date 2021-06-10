package Isep.webtechno.model.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
public class Message {

    @Id
    @GeneratedValue
    private Integer id;

    @ManyToOne
    private User userSending;

//    @ManyToOne
//    private User userReceiving;

    @JsonIgnore
    @ManyToOne
    Conversation conversation;

    private String text;

    private Date date;

}
