package br.com.edvan.loan_calculator_api.util;

import br.com.edvan.loan_calculator_api.model.CompetenceDate;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.*;

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
            LocalDate start,
            LocalDate end
    ) {

        LocalDate current = start;

        while (!current.isAfter(end)) {

            LocalDate lastDay = YearMonth.from(current).atEndOfMonth();

            if (!lastDay.isBefore(start) && !lastDay.isAfter(end)) {
                dates.add(lastDay);
            }

            current = current.plusMonths(1).withDayOfMonth(1);
        }
    }

    private void addPaymentDates(
            Set<LocalDate> dates,
            LocalDate payment,
            LocalDate end
    ) {

        int desiredDay = payment.getDayOfMonth();

        LocalDate current = payment;

        while (!current.isAfter(end)) {

            dates.add(current);

            YearMonth nextMonth = YearMonth.from(current).plusMonths(1);

            current = nextMonth.atDay(
                    Math.min(desiredDay, nextMonth.lengthOfMonth())
            );
        }
    }

    private boolean isPaymentDate(
            LocalDate date,
            LocalDate firstPayment,
            LocalDate end
    ) {

        int desiredDay = firstPayment.getDayOfMonth();

        LocalDate current = firstPayment;

        while (!current.isAfter(end)) {

            if (current.equals(date)) {
                return true;
            }

            YearMonth nextMonth = YearMonth.from(current).plusMonths(1);

            current = nextMonth.atDay(
                    Math.min(desiredDay, nextMonth.lengthOfMonth())
            );
        }

        return false;
    }
}
