package com.cauadev.teste_pratico_fsbr.domain.reservation;

import com.cauadev.teste_pratico_fsbr.domain.customer.Customer;
import com.cauadev.teste_pratico_fsbr.domain.parking.ParkingSpot;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "reservas")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "dt_entrada", nullable = false)
    private LocalDateTime startDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "dt_saida")
    private LocalDateTime endDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parking_spot_id", nullable = false)
    private ParkingSpot parkingSpot;


    public Reservation(Long id, LocalDateTime startDate, LocalDateTime endDate, Customer customer, ParkingSpot parkingSpot) {
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.customer = customer;
        this.parkingSpot = parkingSpot;
    }

    public Reservation() {}


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public ParkingSpot getParkingSpot() {
        return parkingSpot;
    }



    public void setParkingSpot(ParkingSpot parkingSpot) {
        this.parkingSpot = parkingSpot;
    }
}
