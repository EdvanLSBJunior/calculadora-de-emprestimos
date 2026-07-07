package br.com.edvan.loan_calculator_api.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;

public record LoanCalculationRequest(

        @NotNull
        LocalDate startDate,

        @NotNull
        LocalDate endDate,

        @NotNull
        LocalDate firstPaymentDate,

        @NotNull
        @DecimalMin("0.01")
        BigDecimal loanAmount,

        @NotNull
        @DecimalMin("0.0001")
        BigDecimal interestRate

) {
}
