package Isep.webtechno.model.repo;

import Isep.webtechno.model.entity.Booking;
import org.springframework.data.repository.CrudRepository;

public interface BookingRepository extends CrudRepository<Booking, Integer> {
}
