package com.travelcompany.booking.controller;

import com.travelcompany.booking.dto.FareCalculationDto;
import com.travelcompany.booking.service.FareCalculatorService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import java.math.BigDecimal;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Unit tests for FareController
 */
@WebMvcTest(FareController.class)
class FareControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FareCalculatorService fareCalculatorService;

    @Test
    void testCalculateFare() throws Exception {
        BigDecimal expectedFare = new BigDecimal("15.50");
        given(fareCalculatorService.calculateFare("123 Main St", "456 Oak Ave", "Economy"))
            .willReturn(expectedFare);

        mockMvc.perform(post("/api/fare/calculate")
                .param("pickupLocation", "123 Main St")
                .param("destination", "456 Oak Ave")
                .param("cabType", "Economy"))
                .andExpect(status().isOk())
                .andExpect(content().string("15.50"));
    }

    @Test
    void testCalculateDetailedFare() throws Exception {
        FareCalculationDto fareDto = new FareCalculationDto("123 Main St", "456 Oak Ave", "Economy", new BigDecimal("5.0"));
        fareDto.setBaseFare(new BigDecimal("5.00"));
        fareDto.setDistanceFare(new BigDecimal("7.50"));
        fareDto.setTimeFare(new BigDecimal("3.00"));
        fareDto.setTotalFare(new BigDecimal("15.50"));

        given(fareCalculatorService.calculateDetailedFare("123 Main St", "456 Oak Ave", "Economy"))
            .willReturn(fareDto);

        mockMvc.perform(post("/api/fare/detailed")
                .param("pickupLocation", "123 Main St")
                .param("destination", "456 Oak Ave")
                .param("cabType", "Economy"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalFare").value(15.50))
                .andExpect(jsonPath("$.baseFare").value(5.00))
                .andExpect(jsonPath("$.distanceFare").value(7.50));
    }

    @Test
    void testGetEstimatedTime() throws Exception {
        given(fareCalculatorService.getEstimatedTripTime("123 Main St", "456 Oak Ave"))
            .willReturn(20);

        mockMvc.perform(get("/api/fare/time-estimate")
                .param("pickupLocation", "123 Main St")
                .param("destination", "456 Oak Ave"))
                .andExpect(status().isOk())
                .andExpect(content().string("20"));
    }
}