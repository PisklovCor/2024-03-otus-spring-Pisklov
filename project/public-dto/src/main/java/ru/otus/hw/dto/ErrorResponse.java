package ru.otus.hw.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

import static java.time.temporal.ChronoUnit.SECONDS;

@Schema(description = "Ответ в случаи ошибки")
@Data
public class ErrorResponse {

    /**
     * Time exception.
     */
    @Schema(description = "Время")
    private final LocalDateTime timestamp = LocalDateTime.now()
            .truncatedTo(SECONDS);

    /**
     * Type exception.
     */
    @Schema(description = "Тип")
    private String exceptionType;

    /**
     * Cause exception.
     */
    @Schema(description = "Стек")
    private List<String> cause;

}
