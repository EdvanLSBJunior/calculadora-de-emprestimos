package br.com.edvan.loan_calculator_api.service;

import br.com.edvan.loan_calculator_api.dto.request.LoanCalculationRequest;
import br.com.edvan.loan_calculator_api.dto.response.LoanCalculationResponse;
import br.com.edvan.loan_calculator_api.util.CompetenceDateGenerator;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoanCalculatorService {

    private final CompetenceDateGenerator competenceDateGenerator;

    public LoanCalculatorService(CompetenceDateGenerator competenceDateGenerator) {
        this.competenceDateGenerator = competenceDateGenerator;
    }

    public List<LoanCalculationResponse> calculate(
            LoanCalculationRequest request
    ) {

        return competenceDateGenerator.generate(
                        request.startDate(),
                        request.endDate(),
                        request.firstPaymentDate()
                )
                .stream()
                .map(item -> new LoanCalculationResponse(item.getDate()))
                .toList();
    }
}
