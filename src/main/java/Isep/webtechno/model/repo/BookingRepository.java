package Isep.webtechno.model.repo;

import Isep.webtechno.model.entity.Booking;
import Isep.webtechno.model.entity.House;
import Isep.webtechno.model.entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface BookingRepository extends CrudRepository<Booking, Integer> {
    List<Booking> findAllByUser1(User user);
    List<Booking> findAllByUser2(User user);
    List<Booking> findAllByHouseWantedByUser1(House house);
    List<Booking> findAllByHouseWantedByUser2(House house);
}
