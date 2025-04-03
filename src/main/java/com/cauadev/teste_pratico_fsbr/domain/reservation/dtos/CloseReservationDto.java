package com.cauadev.teste_pratico_fsbr.domain.reservation.dtos;

import com.cauadev.teste_pratico_fsbr.domain.customer.Customer;
import com.cauadev.teste_pratico_fsbr.domain.parking.ParkingSpot;
import com.cauadev.teste_pratico_fsbr.domain.parking.enums.ParkingSpotStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record CloseReservationDto(
        @JsonFormat(pattern = "dd/MM/yyyy HH:mm") LocalDateTime startDate,
        @JsonFormat(pattern = "dd/MM/yyyy HH:mm") LocalDateTime endDate,
        BigDecimal price,
        @JsonIgnore ParkingSpot parkingSpot,
        @JsonIgnore Customer customer
) {
}
