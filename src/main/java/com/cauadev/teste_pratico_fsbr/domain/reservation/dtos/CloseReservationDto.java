package com.cauadev.teste_pratico_fsbr.domain.reservation.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record CloseReservationDto(
        @JsonFormat(pattern = "dd/MM/yyyy HH:mm") LocalDateTime startDate,
        @JsonFormat(pattern = "dd/MM/yyyy HH:mm") LocalDateTime endDate,
        BigDecimal price
) {
}
