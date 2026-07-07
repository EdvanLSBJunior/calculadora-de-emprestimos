package br.com.edvan.loan_calculator_api.dto.response;

import java.math.BigDecimal;
import java.time.LocalDate;

public record LoanCalculationResponse(
        LocalDate competenceDate,
        BigDecimal loanAmount,
        BigDecimal debtorBalance,
        String installment,
        BigDecimal total,
        BigDecimal amortization,
        BigDecimal principalBalance,
        BigDecimal interestProvision,
        BigDecimal accumulatedInterest,
        BigDecimal paidInterest
) {
}
