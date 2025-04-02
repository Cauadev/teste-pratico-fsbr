package com.cauadev.teste_pratico_fsbr.domain.parking;

import com.cauadev.teste_pratico_fsbr.domain.parking.enums.ParkingSpotStatus;
import com.cauadev.teste_pratico_fsbr.domain.parking.enums.ParkingSpotType;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;

public class ParkingSpotSpecification implements Specification<ParkingSpot> {

    private final String code;
    private final ParkingSpotType type;
    private final ParkingSpotStatus status;

    public ParkingSpotSpecification(String code, ParkingSpotType type, ParkingSpotStatus status) {
        this.code = code;
        this.type = type;
        this.status = status;
    }

    @Override
    public Predicate toPredicate(Root<ParkingSpot> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        Predicate predicate = criteriaBuilder.conjunction();

        if (code != null && !code.isEmpty()) {
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("code"), code));
        }
        if (type != null) {
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("type"), type));
        }
        if (status != null) {
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("status"), status));
        }

        return predicate;
    }
}
