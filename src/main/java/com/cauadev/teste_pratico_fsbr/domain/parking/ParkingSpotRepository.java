package com.cauadev.teste_pratico_fsbr.domain.parking;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface ParkingSpotRepository extends JpaRepository<ParkingSpot, Long>, JpaSpecificationExecutor<ParkingSpot> {
    Optional<ParkingSpot> findByCode(String code);
    boolean existsByCode(String code);
}