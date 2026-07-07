package br.com.edvan.loan_calculator_api.domain.calculator;

import br.com.edvan.loan_calculator_api.domain.model.CompetenceDate;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

@Component
public class CompetenceDateGenerator {

    public List<CompetenceDate> generate(
            LocalDate startDate,
            LocalDate endDate,
            LocalDate firstPaymentDate
    ) {
        Set<LocalDate> dates = new TreeSet<>();

        dates.add(startDate);
        dates.add(endDate);

        addEndOfMonths(dates, startDate, endDate);
        addPaymentDates(dates, firstPaymentDate, endDate);

        return dates.stream()
                .map(date -> new CompetenceDate(
                        date,
                        isPaymentDate(date, firstPaymentDate, endDate)
                ))
                .toList();
    }

    private void addEndOfMonths(
            Set<LocalDate> dates,
            LocalDate startDate,
            LocalDate endDate
    ) {
        LocalDate current = startDate;

        while (!current.isAfter(endDate)) {
            LocalDate lastDayOfMonth = YearMonth.from(current).atEndOfMonth();

            if (!lastDayOfMonth.isBefore(startDate)
                    && !lastDayOfMonth.isAfter(endDate)) {
                dates.add(lastDayOfMonth);
            }

            current = current.plusMonths(1).withDayOfMonth(1);
        }
    }

    private void addPaymentDates(
            Set<LocalDate> dates,
            LocalDate firstPaymentDate,
            LocalDate endDate
    ) {
        int desiredDay = firstPaymentDate.getDayOfMonth();
        LocalDate current = firstPaymentDate;

        while (current.isBefore(endDate)) {
            dates.add(current);

            YearMonth nextMonth = YearMonth.from(current).plusMonths(1);
            int day = Math.min(desiredDay, nextMonth.lengthOfMonth());

            current = nextMonth.atDay(day);
        }

        dates.add(endDate);
    }

    private boolean isPaymentDate(
            LocalDate date,
            LocalDate firstPaymentDate,
            LocalDate endDate
    ) {
        if (date.equals(endDate)) {
            return true;
        }

        int desiredDay = firstPaymentDate.getDayOfMonth();
        LocalDate current = firstPaymentDate;

        while (current.isBefore(endDate)) {
            if (current.equals(date)) {
                return true;
            }

            YearMonth nextMonth = YearMonth.from(current).plusMonths(1);
            int day = Math.min(desiredDay, nextMonth.lengthOfMonth());

            current = nextMonth.atDay(day);
        }

        return false;
    }
}
