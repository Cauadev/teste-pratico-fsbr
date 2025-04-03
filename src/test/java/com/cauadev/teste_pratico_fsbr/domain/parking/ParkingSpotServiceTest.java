package com.cauadev.teste_pratico_fsbr.domain.parking;

import com.cauadev.teste_pratico_fsbr.domain.parking.dtos.CreateParkingSpotDto;
import com.cauadev.teste_pratico_fsbr.domain.parking.enums.ParkingSpotStatus;
import com.cauadev.teste_pratico_fsbr.domain.parking.enums.ParkingSpotType;
import com.cauadev.teste_pratico_fsbr.infra.exceptions.BusinessException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ParkingSpotServiceTest {

    @Autowired
    private ParkingSpotService service;

    @Autowired
    private ParkingSpotRepository repository;

    @BeforeEach
    void setUp() {
        repository.deleteAll();
    }

    @Test
    void shouldCreateParkingSpot() {
        CreateParkingSpotDto dto = new CreateParkingSpotDto("A1", new BigDecimal(10.0), ParkingSpotType.COMMON);
        ParkingSpot parkingSpot = service.create(dto);

        assertNotNull(parkingSpot.getId());
        assertEquals("A1", parkingSpot.getCode());
        assertEquals(new BigDecimal(10.0), parkingSpot.getHourlyRate());
        assertEquals(ParkingSpotType.COMMON, parkingSpot.getType());
        assertEquals(ParkingSpotStatus.AVAILABLE, parkingSpot.getStatus());
    }

    @Test
    void shouldNotAllowCreateParkingSpotWithDuplicateCode() {
        CreateParkingSpotDto dto = new CreateParkingSpotDto("A1", new BigDecimal(10.0), ParkingSpotType.COMMON);
        final ParkingSpot parkingSpot = service.create(dto);

        BusinessException thrown = assertThrows(BusinessException.class, () -> service.create(dto));
        assertEquals("Já existe uma vaga cadastrada com mesmo identificador", thrown.getMessage());
    }

    @Test
    void shouldCreateParkingSpotVip() {
        CreateParkingSpotDto dto = new CreateParkingSpotDto("A1", new BigDecimal(20.0), ParkingSpotType.VIP);
        ParkingSpot parkingSpot = service.create(dto);

        assertNotNull(parkingSpot.getId());
        assertEquals("A1", parkingSpot.getCode());
        assertEquals(new BigDecimal(20.0), parkingSpot.getHourlyRate());
        assertEquals(ParkingSpotType.VIP, parkingSpot.getType());
        assertEquals(ParkingSpotStatus.AVAILABLE, parkingSpot.getStatus());
    }
}
