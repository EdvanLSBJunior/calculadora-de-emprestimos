package br.com.edvan.loan_calculator_api.service;

import br.com.edvan.loan_calculator_api.domain.calculator.CompetenceDateGenerator;
import br.com.edvan.loan_calculator_api.domain.calculator.InstallmentGenerator;
import br.com.edvan.loan_calculator_api.domain.calculator.InterestCalculator;
import br.com.edvan.loan_calculator_api.domain.model.CompetenceDate;
import br.com.edvan.loan_calculator_api.domain.model.LoanCalculationRow;
import br.com.edvan.loan_calculator_api.dto.request.LoanCalculationRequest;
import br.com.edvan.loan_calculator_api.dto.response.LoanCalculationResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.math.BigDecimal;
import java.math.RoundingMode;


@Service
public class LoanCalculatorService {

    private final CompetenceDateGenerator competenceDateGenerator;
    private final InstallmentGenerator installmentGenerator;
    private final InterestCalculator interestCalculator;

    public LoanCalculatorService(
            CompetenceDateGenerator competenceDateGenerator,
            InstallmentGenerator installmentGenerator,
            InterestCalculator interestCalculator
    ) {
        this.competenceDateGenerator = competenceDateGenerator;
        this.installmentGenerator = installmentGenerator;
        this.interestCalculator = interestCalculator;
    }

    public List<LoanCalculationResponse> calculate(LoanCalculationRequest request) {
        validate(request);
        List<CompetenceDate> competenceDates = competenceDateGenerator.generate(
                request.startDate(),
                request.endDate(),
                request.firstPaymentDate()
        );

        List<LoanCalculationRow> rows = competenceDates.stream()
                .map(item -> new LoanCalculationRow(
                        item.getDate(),
                        item.isPaymentDate()
                ))
                .collect(Collectors.toList());

        int totalInstallments = installmentGenerator.countInstallments(competenceDates);

        installmentGenerator.applyInstallments(rows);

        interestCalculator.calculate(
                rows,
                request.loanAmount(),
                request.interestRate(),
                totalInstallments
        );

        return rows.stream()
                .map(row -> new LoanCalculationResponse(
                        row.getCompetenceDate(),
                        money(row.getLoanAmount()),
                        money(row.getDebtorBalance()),
                        row.getInstallmentNumber() == 0 ? "" : row.getInstallmentNumber() + "/" + totalInstallments,
                        money(row.getTotal()),
                        money(row.getAmortization()),
                        money(row.getPrincipalBalance()),
                        money(row.getInterestProvision()),
                        money(row.getAccumulatedInterest()),
                        money(row.getPaidInterest())
                ))
                .toList();
    }

    private void validate(LoanCalculationRequest request) {
        if (!request.endDate().isAfter(request.startDate())) {
            throw new IllegalArgumentException("A data final deve ser maior que a data inicial.");
        }

        if (!request.firstPaymentDate().isAfter(request.startDate())) {
            throw new IllegalArgumentException("A data do primeiro pagamento deve ser maior que a data inicial.");
        }

        if (!request.firstPaymentDate().isBefore(request.endDate())) {
            throw new IllegalArgumentException("A data do primeiro pagamento deve ser menor que a data final.");
        }
    }

    private BigDecimal money(BigDecimal value) {
        if (value == null) {
            return BigDecimal.ZERO;
        }

        return value.setScale(2, RoundingMode.HALF_UP);
    }
}
