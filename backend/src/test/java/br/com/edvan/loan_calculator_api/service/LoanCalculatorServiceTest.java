package br.com.edvan.loan_calculator_api.service;

import br.com.edvan.loan_calculator_api.domain.calculator.CompetenceDateGenerator;
import br.com.edvan.loan_calculator_api.domain.calculator.InstallmentGenerator;
import br.com.edvan.loan_calculator_api.domain.calculator.InterestCalculator;
import br.com.edvan.loan_calculator_api.dto.request.LoanCalculationRequest;
import br.com.edvan.loan_calculator_api.dto.response.LoanCalculationResponse;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LoanCalculatorServiceTest {

    private final LoanCalculatorService service = new LoanCalculatorService(
            new CompetenceDateGenerator(),
            new InstallmentGenerator(),
            new InterestCalculator()
    );

    @Test
    void shouldCalculateLoanAccordingToSpreadsheetExample() {
        LoanCalculationRequest request = new LoanCalculationRequest(
                LocalDate.of(2024, 1, 1),
                LocalDate.of(2034, 1, 1),
                LocalDate.of(2024, 2, 15),
                BigDecimal.valueOf(140000),
                BigDecimal.valueOf(0.07)
        );

        List<LoanCalculationResponse> result = service.calculate(request);

        assertEquals(241, result.size());

        LoanCalculationResponse firstRow = result.get(0);
        assertEquals(LocalDate.of(2024, 1, 1), firstRow.competenceDate());
        assertEquals(new BigDecimal("140000.00"), firstRow.loanAmount());
        assertEquals(new BigDecimal("140000.00"), firstRow.debtorBalance());
        assertEquals("", firstRow.installment());

        LoanCalculationResponse secondRow = result.get(1);
        assertEquals(LocalDate.of(2024, 1, 31), secondRow.competenceDate());
        assertEquals(new BigDecimal("140791.58"), secondRow.debtorBalance());
        assertEquals(new BigDecimal("791.58"), secondRow.interestProvision());
        assertEquals(new BigDecimal("791.58"), secondRow.accumulatedInterest());

        LoanCalculationResponse firstPayment = result.get(2);
        assertEquals(LocalDate.of(2024, 2, 15), firstPayment.competenceDate());
        assertEquals("1/120", firstPayment.installment());
        assertEquals(new BigDecimal("2355.71"), firstPayment.total());
        assertEquals(new BigDecimal("1166.67"), firstPayment.amortization());
        assertEquals(new BigDecimal("138833.33"), firstPayment.principalBalance());
        assertEquals(new BigDecimal("397.47"), firstPayment.interestProvision());
        assertEquals(new BigDecimal("1189.05"), firstPayment.paidInterest());

        LoanCalculationResponse lastRow = result.get(result.size() - 1);
        assertEquals(LocalDate.of(2034, 1, 1), lastRow.competenceDate());
        assertEquals("120/120", lastRow.installment());
        assertEquals(new BigDecimal("1170.40"), lastRow.total());
        assertEquals(new BigDecimal("1166.67"), lastRow.amortization());
        assertEquals(new BigDecimal("0.00"), lastRow.principalBalance());
        assertEquals(new BigDecimal("0.22"), lastRow.interestProvision());
        assertEquals(new BigDecimal("3.73"), lastRow.paidInterest());
        assertEquals(new BigDecimal("0.00"), lastRow.debtorBalance());
    }

    @Test
    void shouldThrowExceptionWhenEndDateIsBeforeStartDate() {
        LoanCalculationRequest request = new LoanCalculationRequest(
                LocalDate.of(2024, 1, 1),
                LocalDate.of(2023, 1, 1),
                LocalDate.of(2024, 2, 15),
                BigDecimal.valueOf(140000),
                BigDecimal.valueOf(0.07)
        );

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> service.calculate(request)
        );

        assertEquals("A data final deve ser maior que a data inicial.", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenFirstPaymentDateIsBeforeStartDate() {
        LoanCalculationRequest request = new LoanCalculationRequest(
                LocalDate.of(2024, 1, 1),
                LocalDate.of(2034, 1, 1),
                LocalDate.of(2023, 12, 15),
                BigDecimal.valueOf(140000),
                BigDecimal.valueOf(0.07)
        );

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> service.calculate(request)
        );

        assertEquals("A data do primeiro pagamento deve ser maior que a data inicial.", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenFirstPaymentDateIsAfterEndDate() {
        LoanCalculationRequest request = new LoanCalculationRequest(
                LocalDate.of(2024, 1, 1),
                LocalDate.of(2034, 1, 1),
                LocalDate.of(2035, 1, 1),
                BigDecimal.valueOf(140000),
                BigDecimal.valueOf(0.07)
        );

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> service.calculate(request)
        );

        assertEquals("A data do primeiro pagamento deve ser menor que a data final.", exception.getMessage());
    }
}
