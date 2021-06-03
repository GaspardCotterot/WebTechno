package Isep.webtechno.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.sun.istack.NotNull;
import lombok.Data;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "houses")
public class House {
    @Id
    @GeneratedValue
    private Integer id;

    @NotNull
    private String title;

    private String description = "";

    private String address = "";

    private String city = "";

    private Integer postalCode;

    private String country = "";

//    @JsonIgnore
//    @OneToMany(mappedBy = "house")
//    private List<Booking> bookings;

    @ManyToMany()
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<HouseConstraint> constraints;

    @ManyToMany()
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<HouseService> services;

    @JsonManagedReference
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "house")
    private List<Picture> pictures = new ArrayList<>(3);

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(referencedColumnName = "id")
    private User owner;


}
