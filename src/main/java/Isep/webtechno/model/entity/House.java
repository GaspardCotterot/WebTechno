package Isep.webtechno.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Table(name = "houses")
public class House {
    @Id
    @GeneratedValue
    private Integer id;

    private String title;

    private String description;

    @ManyToMany
    private List<HouseConstraint> constraints;

    @ManyToMany
    private List<HouseService> services;

    @JsonBackReference
    @ManyToOne
    @JoinColumn
    private User owner;


}
