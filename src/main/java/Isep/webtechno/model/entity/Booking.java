package Isep.webtechno.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;
import java.util.List;


@Entity
@Data
@Table(name = "bookings")
public class Booking {//todo refacto
    @Id
    @GeneratedValue
    private Integer id;

    private BookingState state;

    //    @JsonBackReference
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(referencedColumnName = "id")
    private User user1; //user sending

    //    @JsonBackReference
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(referencedColumnName = "id")
    private User user2;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(referencedColumnName = "id")
    private House houseWantedByUser1;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(referencedColumnName = "id")
    private House houseWantedByUser2;


    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startDateHouse1;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endDateHouse1;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startDateHouse2;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endDateHouse2;

    private boolean hasUser1Accepted = false;

    private boolean hasUser2Accepted = false;


}
