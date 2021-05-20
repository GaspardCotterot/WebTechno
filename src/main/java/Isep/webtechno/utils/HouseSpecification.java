package Isep.webtechno.utils;

import Isep.webtechno.model.entity.House;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class HouseSpecification implements Specification<House> {

    private final House criteria;

    public HouseSpecification(House criteria) {
        this.criteria = criteria;
    }

    @Override
    public Predicate toPredicate(Root<House> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {

        return null;
    }
}
