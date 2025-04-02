package com.cauadev.teste_pratico_fsbr.domain.parking;

import com.cauadev.teste_pratico_fsbr.domain.parking.dtos.CreateParkingSpotDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.Optional;

public interface ParkingSpotService {
    Page<ParkingSpot> findAll(ParkingSpotSpecification spec, Pageable pageable);
    ParkingSpot create(CreateParkingSpotDto dto);
    Optional<ParkingSpot> findByCode(String code);
    ParkingSpot save(ParkingSpot parkingSpot);
}
