package br.com.edvan.loan_calculator_api.controller;

import br.com.edvan.loan_calculator_api.dto.request.LoanCalculationRequest;
import br.com.edvan.loan_calculator_api.dto.response.LoanCalculationResponse;
import br.com.edvan.loan_calculator_api.service.LoanCalculatorService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/loan-calculator")
public class LoanCalculatorController {

    private final LoanCalculatorService service;

    public LoanCalculatorController(LoanCalculatorService service) {
        this.service = service;
    }

    @PostMapping("/calculate")
    public List<LoanCalculationResponse> calculate(
            @RequestBody @Valid LoanCalculationRequest request
    ) {
        return service.calculate(request);
    }

}