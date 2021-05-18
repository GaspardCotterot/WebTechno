package Isep.webtechno.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import lombok.NonNull;

import javax.persistence.*;
import java.util.List;


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

    @OneToMany(mappedBy = "booking")
    private List<Message> messages;

    @JsonBackReference
    @ManyToOne
    @JoinColumn
    private House house;

}
