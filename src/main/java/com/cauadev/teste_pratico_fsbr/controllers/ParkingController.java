package com.cauadev.teste_pratico_fsbr.controllers;

import com.cauadev.teste_pratico_fsbr.domain.parking.ParkingSpot;
import com.cauadev.teste_pratico_fsbr.domain.parking.ParkingSpotService;
import com.cauadev.teste_pratico_fsbr.domain.parking.ParkingSpotSpecification;
import com.cauadev.teste_pratico_fsbr.domain.parking.dtos.CreateParkingSpotDto;
import jakarta.validation.Valid;
import net.kaczmarzyk.spring.data.jpa.domain.Equal;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/parking/spots")
public class ParkingController {

    private final ParkingSpotService service;

    public ParkingController(ParkingSpotService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<Page<ParkingSpot>> getAll(
            ParkingSpotSpecification spec,
            @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable
    ){
        return ResponseEntity.status(HttpStatus.OK).body(service.findAll(spec, pageable));
    }

    @PostMapping
    public ResponseEntity<ParkingSpot> create(@RequestBody @Valid CreateParkingSpotDto dto){
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(dto));
    }
}
