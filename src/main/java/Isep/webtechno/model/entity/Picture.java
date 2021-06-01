package Isep.webtechno.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Picture {
    @Id
    @GeneratedValue
    private Integer id;

    private boolean isFromInternet = false;

    private String url;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(referencedColumnName = "id")
    private House house;
}
