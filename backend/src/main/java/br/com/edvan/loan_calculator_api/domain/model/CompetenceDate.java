package br.com.edvan.loan_calculator_api.domain.model;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public class CompetenceDate {

    private LocalDate date;
    private boolean paymentDate;
}
