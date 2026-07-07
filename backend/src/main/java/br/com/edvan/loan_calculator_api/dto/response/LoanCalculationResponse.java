package br.com.edvan.loan_calculator_api.dto.response;

import java.time.LocalDate;

public record LoanCalculationResponse(
        LocalDate competenceDate
) {
}
