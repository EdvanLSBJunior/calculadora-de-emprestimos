package br.com.edvan.loan_calculator_api.domain.calculator;

import br.com.edvan.loan_calculator_api.domain.model.LoanCalculationRow;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class InterestCalculatorTest {

    private final InterestCalculator calculator = new InterestCalculator();

    @Test
    void shouldCalculateFinancialValuesForRows() {
        List<LoanCalculationRow> rows = new ArrayList<>();

        LoanCalculationRow start = new LoanCalculationRow(
                LocalDate.of(2024, 1, 1),
                false
        );

        LoanCalculationRow monthEnd = new LoanCalculationRow(
                LocalDate.of(2024, 1, 31),
                false
        );

        LoanCalculationRow firstPayment = new LoanCalculationRow(
                LocalDate.of(2024, 2, 15),
                true
        );

        firstPayment.setInstallmentNumber(1);

        rows.add(start);
        rows.add(monthEnd);
        rows.add(firstPayment);

        calculator.calculate(
                rows,
                BigDecimal.valueOf(140000),
                BigDecimal.valueOf(0.07),
                120
        );

        assertEquals(new BigDecimal("140000.00"), money(start.getLoanAmount()));
        assertEquals(new BigDecimal("140000.00"), money(start.getDebtorBalance()));
        assertEquals(new BigDecimal("0.00"), money(start.getInterestProvision()));

        assertEquals(new BigDecimal("140791.58"), money(monthEnd.getDebtorBalance()));
        assertEquals(new BigDecimal("791.58"), money(monthEnd.getInterestProvision()));
        assertEquals(new BigDecimal("791.58"), money(monthEnd.getAccumulatedInterest()));

        assertEquals(new BigDecimal("1166.67"), money(firstPayment.getAmortization()));
        assertEquals(new BigDecimal("138833.33"), money(firstPayment.getPrincipalBalance()));
        assertEquals(new BigDecimal("397.47"), money(firstPayment.getInterestProvision()));
        assertEquals(new BigDecimal("1189.05"), money(firstPayment.getPaidInterest()));
        assertEquals(new BigDecimal("138833.33"), money(firstPayment.getDebtorBalance()));
    }

    private BigDecimal money(BigDecimal value) {
        return value.setScale(2, RoundingMode.HALF_UP);
    }
}
