package Isep.webtechno.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "houses")
public class House {
    @Id
    @GeneratedValue
    private Integer id;

    private String title;

    private String description;

    @JsonBackReference
    @ManyToOne
    @JoinColumn
    private User owner;



}
