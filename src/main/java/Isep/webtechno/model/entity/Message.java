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

    private Boolean isFromOwner;

    private String text;

    private Date date;

    @ManyToOne
    @JoinColumn
    private Booking booking;

}
