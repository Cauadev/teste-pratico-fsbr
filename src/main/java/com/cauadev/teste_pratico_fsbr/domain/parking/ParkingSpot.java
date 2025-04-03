package com.cauadev.teste_pratico_fsbr.domain.parking;

import com.cauadev.teste_pratico_fsbr.domain.parking.enums.ParkingSpotStatus;
import com.cauadev.teste_pratico_fsbr.domain.parking.enums.ParkingSpotType;
import com.cauadev.teste_pratico_fsbr.domain.reservation.Reservation;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Set;

@Entity
@Table(name = "vagas")
public class ParkingSpot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "codigo_vaga", nullable = false, unique = true)
    private String code;

    @Column(name = "valor_hora", nullable = false, precision = 10, scale = 2)
    private BigDecimal hourlyRate;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_vaga", nullable = false)
    private ParkingSpotType type;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private ParkingSpotStatus status;

    @OneToMany(mappedBy = "parkingSpot", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<Reservation> reservations;



    public ParkingSpot() {
    }

    public ParkingSpot(Long id, String code, BigDecimal hourlyRate, ParkingSpotType type, ParkingSpotStatus status, Set<Reservation> reservations) {
        this.id = id;
        this.code = code;
        this.hourlyRate = hourlyRate;
        this.type = type;
        this.status = status;
        this.reservations = reservations;
    }

    public ParkingSpot(String code, BigDecimal hourlyRate, ParkingSpotType type) {
        this.code = code;
        this.hourlyRate = hourlyRate;
        this.type = type;
    }

    public ParkingSpot(String code, BigDecimal hourlyRate, ParkingSpotType type, ParkingSpotStatus status) {
        this.code = code;
        this.hourlyRate = hourlyRate;
        this.type = type;
        this.status = status;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getHourlyRate() {
        return hourlyRate;
    }

    public void setHourlyRate(BigDecimal hourlyRate) {
        this.hourlyRate = hourlyRate;
    }

    public ParkingSpotType getType() {
        return type;
    }

    public void setType(ParkingSpotType type) {
        this.type = type;
    }

    public ParkingSpotStatus getStatus() {
        return status;
    }

    public void setStatus(ParkingSpotStatus status) {
        this.status = status;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Set<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(Set<Reservation> reservations) {
        this.reservations = reservations;
    }
}
