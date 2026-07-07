package br.com.edvan.loan_calculator_api.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
public class CalculationContext {

    private BigDecimal loanAmount;

    private BigDecimal interestRate;

    private int totalInstallments;

    private BigDecimal amortization;
}
