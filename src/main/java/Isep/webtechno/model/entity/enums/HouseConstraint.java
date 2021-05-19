package Isep.webtechno.model.entity.enums;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
public class HouseConstraint {

    @Id
    @GeneratedValue
    private Integer id;

    @Column(nullable = false)
    private String title;

    private String description;

    public HouseConstraint(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public HouseConstraint() {

    }
}
