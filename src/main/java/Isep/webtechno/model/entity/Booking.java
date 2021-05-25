package Isep.webtechno.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;
import java.util.List;


@Entity
@Data
@Table(name = "bookings")
public class Booking {
    @Id
    @GeneratedValue
    private Integer id;

    private BookingState state;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endDate;

    @JsonBackReference
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(referencedColumnName = "id")
    private User user;

    @OneToMany(mappedBy = "booking")
    private List<Message> messages;

    @JsonBackReference
    @ManyToOne
    @JoinColumn
    private House house;

}
