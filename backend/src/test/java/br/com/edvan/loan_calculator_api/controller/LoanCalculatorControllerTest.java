package br.com.edvan.loan_calculator_api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import br.com.edvan.loan_calculator_api.dto.request.LoanCalculationRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class LoanCalculatorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper mapper = new ObjectMapper()
            .findAndRegisterModules();

    @Test
    void shouldCalculateLoan() throws Exception {

        LoanCalculationRequest request = new LoanCalculationRequest(
                LocalDate.of(2024,1,1),
                LocalDate.of(2034,1,1),
                LocalDate.of(2024,2,15),
                BigDecimal.valueOf(140000),
                BigDecimal.valueOf(0.07)
        );

        mockMvc.perform(post("/api/loan-calculator/calculate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(241))
                .andExpect(jsonPath("$[0].loanAmount").value(140000))
                .andExpect(jsonPath("$[0].competenceDate").value("2024-01-01"));
    }
}
