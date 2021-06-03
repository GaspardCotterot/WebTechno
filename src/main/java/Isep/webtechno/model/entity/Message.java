package Isep.webtechno.model.entity;


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

    @ManyToOne
    private User userReceiving;

    private String text;

    private Date date;

}
