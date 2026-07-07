package br.com.edvan.loan_calculator_api.domain.calculator;

import br.com.edvan.loan_calculator_api.domain.model.CompetenceDate;
import br.com.edvan.loan_calculator_api.domain.model.LoanCalculationRow;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class InstallmentGenerator {

    public int countInstallments(List<CompetenceDate> competenceDates) {
        return (int) competenceDates.stream()
                .filter(CompetenceDate::isPaymentDate)
                .count();
    }

    public void applyInstallments(List<LoanCalculationRow> rows) {
        int installmentNumber = 1;

        for (LoanCalculationRow row : rows) {
            if (row.isPaymentDate()) {
                row.setInstallmentNumber(installmentNumber);
                installmentNumber++;
            }
        }
    }
}
