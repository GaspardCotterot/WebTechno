package Isep.webtechno.model.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class HouseService {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String title;

    private String description;

    public HouseService(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public HouseService(Integer id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
    }

    public HouseService() {

    }
}
