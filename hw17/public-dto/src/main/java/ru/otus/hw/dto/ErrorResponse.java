package ru.otus.hw.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

import static java.time.temporal.ChronoUnit.SECONDS;

@Data
public class ErrorResponse {

    /**
     * Time exception.
     */
    private final LocalDateTime timestamp = LocalDateTime.now()
            .truncatedTo(SECONDS);

    /**
     * Type exception.
     */
    private String exceptionType;

    /**
     * Cause exception.
     */
    private List<String> cause;
}
