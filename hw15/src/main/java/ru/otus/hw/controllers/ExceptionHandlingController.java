package ru.otus.hw.controllers;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.otus.hw.dto.ErrorResponse;
import ru.otus.hw.exceptions.NotFoundException;

import java.util.List;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@Slf4j
@RestControllerAdvice
public class ExceptionHandlingController {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(BAD_REQUEST)
    public ErrorResponse methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException ex) {
        log.error("Error: " + ex.getMessage(), ex);
        return createErrorResponseBody(ex);
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(NOT_FOUND)
    public ErrorResponse notFoundExceptionHandler(NotFoundException ex) {
        log.error("Error: " + ex.getMessage(), ex);
        return createErrorResponseBody(ex);
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    public ErrorResponse exceptionHandler(Exception ex) {
        log.error("Error: " + ex.getMessage(), ex);
        return createErrorResponseBody(ex);
    }

    private ErrorResponse createErrorResponseBody(Throwable ex) {
        final List<String> cause = ExceptionUtils.getThrowableList(ex).stream()
                .map(Throwable::getMessage)
                .toList();
        var response = new ErrorResponse();
        response.setExceptionType(ex.getClass().getName());
        response.setCause(cause);

        return response;
    }
}
