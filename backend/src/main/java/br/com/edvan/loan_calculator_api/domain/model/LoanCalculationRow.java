package br.com.edvan.loan_calculator_api.domain.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoanCalculationRow {

    private LocalDate competenceDate;
    private boolean paymentDate;
    private int installmentNumber;

    private BigDecimal loanAmount;
    private BigDecimal debtorBalance;
    private BigDecimal total;
    private BigDecimal amortization;
    private BigDecimal principalBalance;
    private BigDecimal interestProvision;
    private BigDecimal accumulatedInterest;
    private BigDecimal paidInterest;

    public LoanCalculationRow(LocalDate competenceDate, boolean paymentDate) {
        this.competenceDate = competenceDate;
        this.paymentDate = paymentDate;
    }

}
