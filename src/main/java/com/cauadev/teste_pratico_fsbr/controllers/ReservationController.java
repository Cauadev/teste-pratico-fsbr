package com.cauadev.teste_pratico_fsbr.controllers;

import com.cauadev.teste_pratico_fsbr.domain.reservation.Reservation;
import com.cauadev.teste_pratico_fsbr.domain.reservation.ReservationService;
import com.cauadev.teste_pratico_fsbr.domain.reservation.dtos.CloseReservationDto;
import com.cauadev.teste_pratico_fsbr.domain.reservation.dtos.CreateReservationDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    private final ReservationService service;

    public ReservationController(ReservationService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Reservation> createReservation(@Validated @RequestBody CreateReservationDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.createReservation(dto));
    }

    @PutMapping("/{id}/close")
    public ResponseEntity<CloseReservationDto> closeReservation(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(service.closeReservation(id));
    }
}
