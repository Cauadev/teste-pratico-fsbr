package com.cauadev.teste_pratico_fsbr.domain.reservation;

import com.cauadev.teste_pratico_fsbr.domain.customer.Customer;
import com.cauadev.teste_pratico_fsbr.domain.customer.CustomerService;
import com.cauadev.teste_pratico_fsbr.domain.parking.ParkingSpot;
import com.cauadev.teste_pratico_fsbr.domain.parking.ParkingSpotService;
import com.cauadev.teste_pratico_fsbr.domain.parking.enums.ParkingSpotStatus;
import com.cauadev.teste_pratico_fsbr.domain.reservation.dtos.CloseReservationDto;
import com.cauadev.teste_pratico_fsbr.domain.reservation.dtos.CreateReservationDto;
import com.cauadev.teste_pratico_fsbr.infra.exceptions.BusinessException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.LocalDateTime;

@Service
public class ReservationServiceImpl implements ReservationService{

    private final ReservationRepository repository;
    private final ParkingSpotService parkingSpotService;
    private final CustomerService customerService;

    public ReservationServiceImpl(ReservationRepository repository, ParkingSpotService parkingSpotService, CustomerService customerService) {
        this.repository = repository;
        this.parkingSpotService = parkingSpotService;
        this.customerService = customerService;
    }

    @Override
    @Transactional
    public Reservation createReservation(CreateReservationDto dto) {
        final ParkingSpot parkingSpot = parkingSpotService.findByCode(dto.parkingSpotCode()).orElseThrow(() -> {
            throw new BusinessException("Vaga informada é inexistente");
        });

        if(parkingSpot.getStatus().equals(ParkingSpotStatus.RESERVED)){
            throw new BusinessException("Vaga já reservada");
        }

        //BUSCA O CLIENTE PELO CPF, CASO NÃO ENCONTRE, CRIA UM NOVO CLIENTE
        final Customer customer = customerService.findByCpf(dto.customer().cpf())
                .orElseGet(() -> customerService.save(new Customer(dto.customer().fullName(), dto.customer().cpf())));

        parkingSpot.setStatus(ParkingSpotStatus.RESERVED);
        parkingSpotService.save(parkingSpot);

        Reservation reservation = new Reservation();
        reservation.setParkingSpot(parkingSpot);
        reservation.setStartDate(LocalDateTime.now());
        reservation.setCustomer(customer);

        return repository.save(reservation);
    }

    @Override
    @Transactional
    public CloseReservationDto closeReservation(Long id) {
        final Reservation reservation = repository.findById(id).orElseThrow(() -> new BusinessException("Reserva informada é inexistente"));
        if(reservation.getEndDate() != null){
            throw new BusinessException("Reserva informada já foi fechada");
        }
        reservation.setEndDate(LocalDateTime.now());
        repository.save(reservation);

        reservation.getParkingSpot().setStatus(ParkingSpotStatus.AVAILABLE);
        parkingSpotService.save(reservation.getParkingSpot());


        final var totalToPay = this.calculateTotalToPay(reservation.getStartDate(), reservation.getEndDate(), reservation.getParkingSpot().getHourlyRate());

        return new CloseReservationDto(reservation.getStartDate(), reservation.getEndDate(), totalToPay, reservation.getParkingSpot(), reservation.getCustomer());
    }


    public static BigDecimal calculateTotalToPay(LocalDateTime startDate, LocalDateTime endDate, BigDecimal hourlyRate) {
        if (startDate == null || endDate == null || hourlyRate == null || endDate.isBefore(startDate)) {
            throw new BusinessException("Datas inválidas ou preço da hora não informado.");
        }

        long minutes = Duration.between(startDate, endDate).toMinutes();
        long hours = (long) Math.ceil(minutes / 60.0);

        return hourlyRate.multiply(BigDecimal.valueOf(hours)).setScale(2, RoundingMode.HALF_UP);
    }

}
