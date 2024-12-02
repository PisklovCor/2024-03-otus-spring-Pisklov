package ru.otus.hw.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

import static java.time.temporal.ChronoUnit.SECONDS;

@Data
public class ErrorResponse {

    private final LocalDateTime timestamp = LocalDateTime.now().truncatedTo(SECONDS);

    private String exceptionType;

    private List<String> cause;
}
