package com.cauadev.teste_pratico_fsbr.domain.reservation.dtos;

import com.cauadev.teste_pratico_fsbr.domain.customer.dtos.CustomerDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateReservationDto(
        @NotBlank(message = "Informe o codigo da vaga") String parkingSpotCode,
        @Valid @NotNull(message = "Informe os dados do cliente") CustomerDto customer
        ) {
}
