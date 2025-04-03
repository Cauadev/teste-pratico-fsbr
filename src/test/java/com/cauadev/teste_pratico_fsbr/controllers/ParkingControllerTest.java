package com.cauadev.teste_pratico_fsbr.controllers;

import com.cauadev.teste_pratico_fsbr.domain.parking.ParkingSpot;
import com.cauadev.teste_pratico_fsbr.domain.parking.ParkingSpotRepository;
import com.cauadev.teste_pratico_fsbr.domain.parking.dtos.CreateParkingSpotDto;
import com.cauadev.teste_pratico_fsbr.domain.parking.enums.ParkingSpotStatus;
import com.cauadev.teste_pratico_fsbr.domain.parking.enums.ParkingSpotType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ParkingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ParkingSpotRepository repository;

    @BeforeEach
    void setup() {
        repository.deleteAll();
    }

    @Test
    void shouldReturnEmptyList_whenNoParkingSpots() throws Exception {
        mockMvc.perform(get("/parking/spots"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isEmpty());
    }

    @Test
    void shouldCreateParkingSpotSuccessfully() throws Exception {
        String requestBody = """
            {
                "code": "A1",
                "hourlyRate": 10.5,
                "type": "COMMON"
            }
        """;

        mockMvc.perform(post("/parking/spots")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.code").value("A1"))
                .andExpect(jsonPath("$.hourlyRate").value(10.5))
                .andExpect(jsonPath("$.type").value("COMMON"));
    }

    @Test
    void shouldReturnBadRequest_whenInvalidData() throws Exception {
        String requestBody = """
            {
                "code": "",
                "hourlyRate": -5,
                "type": "COMMON"
            }
        """;

        mockMvc.perform(post("/parking/spots")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldThrowBadRequest_whenParkingSpotAlreadyExists() throws Exception {
        repository.save(new ParkingSpot("A1", BigDecimal.valueOf(10.5), ParkingSpotType.COMMON, ParkingSpotStatus.AVAILABLE));

        String requestBody = """
            {
                "code": "A1",
                "hourlyRate": 15.0,
                "type": "VIP"
            }
        """;

        mockMvc.perform(post("/parking/spots")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isBadRequest());
    }
}
