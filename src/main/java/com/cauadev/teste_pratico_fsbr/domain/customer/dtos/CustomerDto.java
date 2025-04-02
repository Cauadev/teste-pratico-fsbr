package com.cauadev.teste_pratico_fsbr.domain.customer.dtos;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.br.CPF;

public record CustomerDto(
        @NotBlank(message = "Informe o nome do cliente") String fullName,
        @CPF(message = "Informe um cpf válido") @NotBlank(message = "Informe o cpf do cliente") String cpf
) {
}
