package br.com.edvan.loan_calculator_api.model;

import java.time.LocalDate;

public class CompetenceDate {

    private final LocalDate date;
    private final boolean paymentDate;

    public CompetenceDate(LocalDate date, boolean paymentDate) {
        this.date = date;
        this.paymentDate = paymentDate;
    }

    public LocalDate getDate() {
        return date;
    }

    public boolean isPaymentDate() {
        return paymentDate;
    }
}
