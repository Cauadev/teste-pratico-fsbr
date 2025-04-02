package com.cauadev.teste_pratico_fsbr.domain.customer;

import java.util.Optional;

public interface CustomerService {
    Customer save(Customer customer);
    Optional<Customer> findByCpf(String cpf);
}
