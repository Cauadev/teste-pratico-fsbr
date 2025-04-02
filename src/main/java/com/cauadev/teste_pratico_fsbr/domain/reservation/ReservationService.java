package com.cauadev.teste_pratico_fsbr.domain.reservation;

import com.cauadev.teste_pratico_fsbr.domain.reservation.dtos.CloseReservationDto;
import com.cauadev.teste_pratico_fsbr.domain.reservation.dtos.CreateReservationDto;

public interface ReservationService {
    Reservation createReservation(CreateReservationDto dto);
    CloseReservationDto closeReservation(Long id);
}
