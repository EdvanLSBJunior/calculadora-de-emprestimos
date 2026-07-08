package br.com.edvan.loan_calculator_api.domain.calculator;

import br.com.edvan.loan_calculator_api.domain.model.CompetenceDate;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CompetenceDateGeneratorTest {

    private final CompetenceDateGenerator generator =
            new CompetenceDateGenerator();

    @Test
    void shouldGenerateCompetenceDates() {

        List<CompetenceDate> dates = generator.generate(
                LocalDate.of(2024,1,1),
                LocalDate.of(2024,6,15),
                LocalDate.of(2024,2,15)
        );

        assertFalse(dates.isEmpty());

        assertEquals(LocalDate.of(2024,1,1), dates.get(0).getDate());

        assertEquals(LocalDate.of(2024,6,15),
                dates.get(dates.size()-1).getDate());

    }

    @Test
    void shouldContainPaymentDates() {

        List<CompetenceDate> dates = generator.generate(
                LocalDate.of(2024,1,1),
                LocalDate.of(2024,6,15),
                LocalDate.of(2024,2,15)
        );

        long payments = dates.stream()
                .filter(CompetenceDate::isPaymentDate)
                .count();

        assertEquals(5, payments);
    }

    @Test
    void shouldContainMonthEndDates() {

        List<CompetenceDate> dates = generator.generate(
                LocalDate.of(2024,1,1),
                LocalDate.of(2024,6,15),
                LocalDate.of(2024,2,15)
        );

        assertTrue(
                dates.stream()
                        .anyMatch(d -> d.getDate()
                                .equals(LocalDate.of(2024,1,31)))
        );

        assertTrue(
                dates.stream()
                        .anyMatch(d -> d.getDate()
                                .equals(LocalDate.of(2024,2,29)))
        );

        assertTrue(
                dates.stream()
                        .anyMatch(d -> d.getDate()
                                .equals(LocalDate.of(2024,3,31)))
        );
    }
}
