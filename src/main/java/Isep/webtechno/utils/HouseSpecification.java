package Isep.webtechno.utils;

import Isep.webtechno.model.entity.House;
import Isep.webtechno.model.entity.House_;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class HouseSpecification implements Specification<House> {

    private final HouseSearch criteria;

    public HouseSpecification(HouseSearch criteria) {
        this.criteria = criteria;
    }

    @Override
    public Predicate toPredicate(Root<House> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
        final List<Predicate> predicates = new ArrayList<>();
        if (criteria.getLocation() != null) {
            final List<Predicate> locationPredicates = new ArrayList<>();
            String[] locationInfos = criteria.getLocation().split(" ");
            for (String indication : locationInfos) {
                locationPredicates.add(criteriaBuilder.equal(root.get(House_.city), indication));
            }
            for (String indication : locationInfos) {
                locationPredicates.add(criteriaBuilder.equal(root.get(House_.country), indication));
            }
            predicates.add(criteriaBuilder.or(locationPredicates.toArray(new Predicate[locationPredicates.size()])));
        }
        return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
    }
}
