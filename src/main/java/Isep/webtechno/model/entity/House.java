package Isep.webtechno.model.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class House {
    @Id
    @GeneratedValue
    private Integer id;
    private String title;
    private String description;

    @ManyToOne
    @JoinColumn
    private User owner;

}
