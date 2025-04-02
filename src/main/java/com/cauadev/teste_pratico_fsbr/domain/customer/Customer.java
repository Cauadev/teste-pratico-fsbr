package com.cauadev.teste_pratico_fsbr.domain.customer;

import com.cauadev.teste_pratico_fsbr.domain.reservation.Reservation;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "solicitantes")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome", nullable = false)
    private String fullName;

    @Column(nullable = true, unique = true)
    private String cpf;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<Reservation> reservations;

    public Customer() {
    }

    public Customer(String fullName, Long id, String cpf, Set<Reservation> reservations) {
        this.fullName = fullName;
        this.id = id;
        this.cpf = cpf;
        this.reservations = reservations;
    }

    public Customer(String fullName, String cpf) {
        this.fullName = fullName;
        this.cpf = cpf;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public Set<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(Set<Reservation> reservations) {
        this.reservations = reservations;
    }
}
