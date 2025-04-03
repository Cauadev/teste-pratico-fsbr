package com.cauadev.teste_pratico_fsbr.controllers;

import com.cauadev.teste_pratico_fsbr.domain.parking.ParkingSpot;
import com.cauadev.teste_pratico_fsbr.domain.parking.ParkingSpotRepository;
import com.cauadev.teste_pratico_fsbr.domain.parking.ParkingSpotService;
import com.cauadev.teste_pratico_fsbr.domain.parking.ParkingSpotServiceImpl;
import com.cauadev.teste_pratico_fsbr.domain.parking.dtos.CreateParkingSpotDto;
import com.cauadev.teste_pratico_fsbr.domain.parking.enums.ParkingSpotStatus;
import com.cauadev.teste_pratico_fsbr.domain.parking.enums.ParkingSpotType;
import com.cauadev.teste_pratico_fsbr.domain.reservation.ReservationRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ReservationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ReservationRepository repository;

    @Autowired
    private ParkingSpotService parkingSpotService;

    @Autowired
    private ParkingSpotRepository parkingSpotRepository;

    @BeforeAll
    void setup() {
        parkingSpotRepository.deleteAll();
        parkingSpotService.create(new CreateParkingSpotDto("A1", new BigDecimal(10.0), ParkingSpotType.COMMON));
    }

    @Test
    void shouldCreateReservationSuccessfully() throws Exception {
        String requestBody = """
            {
                "parkingSpotCode": "A1",
                "customer": {
                 "fullName": "Teste",
                 "cpf": "55614471065"
                }
            }
        """;

        mockMvc.perform(post("/reservations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.startDate").exists())
                .andExpect(jsonPath("$.endDate").doesNotExist())
                .andExpect(jsonPath("$.customer.fullName").value("Teste"))
                .andExpect(jsonPath("$.customer.cpf").value("55614471065"));
    }

    @Test
    void shouldReturnBadRequest_whenMissData() throws Exception {
        String requestBody = """
            {
                "customer": {
                 "fullName": "Teste",
                 "cpf": "55614471065"
                }
            }
        """;

        mockMvc.perform(post("/reservations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isBadRequest());
    }


    @Test
    void shouldCloseReservationSuccessfully() throws Exception {
        mockMvc.perform(put("/reservations/"+1+"/close")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

}
