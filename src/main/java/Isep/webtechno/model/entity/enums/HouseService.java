package Isep.webtechno.model.entity.enums;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
public class HouseService {

    @Id
    @GeneratedValue
    private Integer id;

    @Column(nullable = false)
    private String title;

    private String description;

    public HouseService(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public HouseService() {

    }
}
