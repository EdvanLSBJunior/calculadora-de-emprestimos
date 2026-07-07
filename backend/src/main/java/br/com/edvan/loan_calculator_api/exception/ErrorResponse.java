package br.com.edvan.loan_calculator_api.exception;

import java.time.LocalDateTime;
import java.util.List;

public record ErrorResponse(
        LocalDateTime timestamp,
        int status,
        String error,
        List<String> messages
) {
}
