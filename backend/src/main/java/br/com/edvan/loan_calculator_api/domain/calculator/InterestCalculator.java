package br.com.edvan.loan_calculator_api.domain.calculator;

import br.com.edvan.loan_calculator_api.domain.model.LoanCalculationRow;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Component
public class InterestCalculator {

    private static final BigDecimal BASE_DAYS = BigDecimal.valueOf(360);
    private static final int SCALE = 10;

    public void calculate(
            List<LoanCalculationRow> rows,
            BigDecimal loanAmount,
            BigDecimal interestRate,
            int totalInstallments
    ) {
        BigDecimal amortizationValue = loanAmount.divide(
                BigDecimal.valueOf(totalInstallments),
                SCALE,
                RoundingMode.HALF_UP
        );

        BigDecimal previousPrincipalBalance = loanAmount;
        BigDecimal previousAccumulatedInterest = BigDecimal.ZERO;
        BigDecimal previousDebtorBalance = loanAmount;

        for (int i = 0; i < rows.size(); i++) {
            LoanCalculationRow currentRow = rows.get(i);

            boolean firstRow = i == 0;

            BigDecimal currentLoanAmount = firstRow ? loanAmount : BigDecimal.ZERO;

            BigDecimal interestProvision = firstRow
                    ? BigDecimal.ZERO
                    : calculateInterestProvision(
                    rows.get(i - 1),
                    currentRow,
                    interestRate,
                    previousDebtorBalance
            );

            BigDecimal amortization = currentRow.isPaymentDate()
                    ? amortizationValue
                    : BigDecimal.ZERO;

            BigDecimal paidInterest = currentRow.isPaymentDate()
                    ? previousAccumulatedInterest.add(interestProvision)
                    : BigDecimal.ZERO;

            BigDecimal principalBalance = previousPrincipalBalance.subtract(amortization);

            BigDecimal accumulatedInterest = previousAccumulatedInterest
                    .add(interestProvision)
                    .subtract(paidInterest);

            BigDecimal debtorBalance = principalBalance.add(accumulatedInterest);

            BigDecimal total = amortization.add(paidInterest);

            currentRow.setLoanAmount(currentLoanAmount);
            currentRow.setAmortization(amortization);
            currentRow.setPaidInterest(paidInterest);
            currentRow.setPrincipalBalance(principalBalance);
            currentRow.setInterestProvision(interestProvision);
            currentRow.setAccumulatedInterest(accumulatedInterest);
            currentRow.setDebtorBalance(debtorBalance);
            currentRow.setTotal(total);

            previousPrincipalBalance = principalBalance;
            previousAccumulatedInterest = accumulatedInterest;
            previousDebtorBalance = debtorBalance;
        }
    }

    private BigDecimal calculateInterestProvision(
            LoanCalculationRow previousRow,
            LoanCalculationRow currentRow,
            BigDecimal interestRate,
            BigDecimal previousDebtorBalance
    ) {
        long days = ChronoUnit.DAYS.between(
                previousRow.getCompetenceDate(),
                currentRow.getCompetenceDate()
        );

        double exponent = BigDecimal.valueOf(days)
                .divide(BASE_DAYS, SCALE, RoundingMode.HALF_UP)
                .doubleValue();

        double factor = Math.pow(
                BigDecimal.ONE.add(interestRate).doubleValue(),
                exponent
        ) - 1;

        return previousDebtorBalance.multiply(BigDecimal.valueOf(factor));
    }
}
