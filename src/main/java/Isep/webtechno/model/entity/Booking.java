package Isep.webtechno.model.entity;

import lombok.Data;
import lombok.NonNull;

import javax.persistence.*;


@Entity
@Data
public class Booking {
    @Id
    @GeneratedValue
    private Integer id;

    private Integer state;

    @ManyToOne
    @JoinColumn
    private User user;


    @ManyToOne
    @JoinColumn
    private House house;

}
