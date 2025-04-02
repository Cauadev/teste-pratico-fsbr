package com.cauadev.teste_pratico_fsbr.domain.parking;

import com.cauadev.teste_pratico_fsbr.domain.parking.dtos.CreateParkingSpotDto;
import com.cauadev.teste_pratico_fsbr.domain.parking.enums.ParkingSpotStatus;
import com.cauadev.teste_pratico_fsbr.infra.exceptions.BusinessException;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ParkingSpotServiceImpl implements ParkingSpotService{

    private ParkingSpotRepository repository;

    public ParkingSpotServiceImpl(ParkingSpotRepository repository) {
        this.repository = repository;
    }

    @Override
    public Page<ParkingSpot> findAll(ParkingSpotSpecification spec, Pageable pageable) {
        return repository.findAll(spec, pageable);
    }

    @Override
    public ParkingSpot create(CreateParkingSpotDto dto) {
        final boolean existsParkingSpot = repository.existsByCode(dto.code());
        if(existsParkingSpot){
            throw new BusinessException("Já existe uma vaga cadastrada com mesmo identificador");
        }
        ParkingSpot parkingSpot = new ParkingSpot();
        BeanUtils.copyProperties(dto, parkingSpot);
        parkingSpot.setStatus(ParkingSpotStatus.AVAILABLE);

        return this.save(parkingSpot);
    }

    @Override
    public Optional<ParkingSpot> findByCode(String code) {
        return repository.findByCode(code);
    }

    @Override
    public ParkingSpot save(ParkingSpot parkingSpot) {
        return repository.save(parkingSpot);
    }
}
