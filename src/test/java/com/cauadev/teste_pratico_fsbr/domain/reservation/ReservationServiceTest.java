package com.cauadev.teste_pratico_fsbr.domain.reservation;

import com.cauadev.teste_pratico_fsbr.domain.customer.dtos.CustomerDto;
import com.cauadev.teste_pratico_fsbr.domain.parking.ParkingSpot;
import com.cauadev.teste_pratico_fsbr.domain.parking.ParkingSpotService;
import com.cauadev.teste_pratico_fsbr.domain.parking.dtos.CreateParkingSpotDto;
import com.cauadev.teste_pratico_fsbr.domain.parking.enums.ParkingSpotStatus;
import com.cauadev.teste_pratico_fsbr.domain.parking.enums.ParkingSpotType;
import com.cauadev.teste_pratico_fsbr.domain.reservation.dtos.CloseReservationDto;
import com.cauadev.teste_pratico_fsbr.domain.reservation.dtos.CreateReservationDto;
import com.cauadev.teste_pratico_fsbr.infra.exceptions.BusinessException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.temporal.ChronoUnit;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ReservationServiceTest {

    @Autowired
    private ReservationService service;

    @Autowired
    private ParkingSpotService parkingSpotService;

    @Autowired
    private ReservationRepository repository;

    @Test
    void shouldCreateReservation() {
        parkingSpotService.create(new CreateParkingSpotDto("A1", new BigDecimal(10.0), ParkingSpotType.COMMON));

        CustomerDto customer = new CustomerDto("Teste", "99999999999");
        CreateReservationDto dto = new CreateReservationDto("A1", customer);
        final Reservation reservation = service.createReservation(dto);

        assertNotNull(reservation.getId());
        assertNotNull(reservation.getStartDate());

        assertEquals("A1", reservation.getParkingSpot().getCode());

        assertNotNull(reservation.getCustomer().getId());
        assertEquals("Teste", reservation.getCustomer().getFullName());
        assertEquals("99999999999", reservation.getCustomer().getCpf());
    }


    @Test
    void shouldNotAllowCreateReservationWithDuplicateParkingSpotCode() {
        CustomerDto customer = new CustomerDto("Teste2", "11111111111");
        CreateReservationDto dto = new CreateReservationDto("A1", customer);

        BusinessException thrown = assertThrows(BusinessException.class, () -> service.createReservation(dto));
        assertEquals("Vaga já reservada", thrown.getMessage());
    }


    @Test
    void shouldCloseReservation() {
        final CloseReservationDto closeReservationDto = service.closeReservation(1L);
        final ParkingSpot parkingSpot = parkingSpotService.findByCode("A1").get();

        assertEquals(ParkingSpotStatus.AVAILABLE, parkingSpot.getStatus());

        assertNotNull(closeReservationDto.startDate());
        assertNotNull(closeReservationDto.endDate());
        assertNotNull(closeReservationDto.price());
    }


    @Test
    void shouldCalculateTotalToPayCorrectly() {
        LocalDateTime startDate = LocalDateTime.of(2024, Month.JANUARY, 1, 10, 0);
        LocalDateTime endDate = startDate.plus(2, ChronoUnit.HOURS);
        BigDecimal hourlyRate = new BigDecimal(50.00);

        BigDecimal total = ReservationServiceImpl.calculateTotalToPay(startDate, endDate, hourlyRate);

        assertEquals(total,  new BigDecimal("100.00"));
    }

    @Test
    void shouldRoundUpToNextHour() {
        LocalDateTime startDate = LocalDateTime.of(2024, Month.JANUARY, 1, 10, 0);
        LocalDateTime endDate = startDate.plusMinutes(90);
        BigDecimal hourlyRate = new BigDecimal(50.00);

        BigDecimal total = ReservationServiceImpl.calculateTotalToPay(startDate, endDate, hourlyRate);

        assertEquals(total, new BigDecimal("100.00"));
    }

    @Test
    void shouldThrowExceptionWhenStartDateIsNull() {
        LocalDateTime endDate = LocalDateTime.now();
        BigDecimal hourlyRate = new BigDecimal(50.00);

        assertThrows(BusinessException.class,
                () -> ReservationServiceImpl.calculateTotalToPay(null, endDate, hourlyRate));
    }

    @Test
    void shouldThrowExceptionWhenEndDateIsNull() {
        LocalDateTime startDate = LocalDateTime.now();
        BigDecimal hourlyRate = new BigDecimal(50.00);

        assertThrows(BusinessException.class,
                () -> ReservationServiceImpl.calculateTotalToPay(startDate, null, hourlyRate));
    }

    @Test
    void shouldThrowExceptionWhenHourlyRateIsNull() {
        LocalDateTime startDate = LocalDateTime.now();
        LocalDateTime endDate = startDate.plusHours(1);

        assertThrows(BusinessException.class,
                () -> ReservationServiceImpl.calculateTotalToPay(startDate, endDate, null));
    }

    @Test
    void shouldThrowExceptionWhenEndDateIsBeforeStartDate() {
        LocalDateTime startDate = LocalDateTime.now();
        LocalDateTime endDate = startDate.minusHours(1);
        BigDecimal hourlyRate = new BigDecimal(50.00);

        assertThrows(BusinessException.class,
                () -> ReservationServiceImpl.calculateTotalToPay(startDate, endDate, hourlyRate));
    }


    
}
