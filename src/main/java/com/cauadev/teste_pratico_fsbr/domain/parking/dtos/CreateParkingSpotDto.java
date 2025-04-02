package com.cauadev.teste_pratico_fsbr.domain.parking.dtos;

import com.cauadev.teste_pratico_fsbr.domain.parking.enums.ParkingSpotType;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record CreateParkingSpotDto(
        @NotBlank(message = "Informe o codigo da vaga") String code,
        @NotNull(message = "Informe o valor/hora") @DecimalMin("0.0") BigDecimal hourlyRate,
        @NotNull(message = "Informe o tipo de vaga") ParkingSpotType type
) {
}
