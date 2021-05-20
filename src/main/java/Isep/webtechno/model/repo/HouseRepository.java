package Isep.webtechno.model.repo;

import Isep.webtechno.model.entity.House;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

public interface HouseRepository extends CrudRepository<House, Integer>, JpaSpecificationExecutor<House> {
}
