package ru.otus.hw.controllers;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.otus.hw.dto.ErrorResponse;
import ru.otus.hw.exceptions.NotFoundException;

import java.util.List;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@Slf4j
@RestControllerAdvice
public class ExceptionHandlingController {

    private static final MediaType CONTENT_TYPE = new MediaType(APPLICATION_JSON, UTF_8);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException ex) {
        return handlerException(ex, BAD_REQUEST);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> notFoundExceptionHandler(NotFoundException ex) {
        return handlerException(ex, NOT_FOUND);
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    public ResponseEntity<ErrorResponse> exceptionHandler(Exception ex) {
        return handlerException(ex, INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<ErrorResponse> handlerException(Throwable ex, HttpStatus status) {
        log.error("Error: " + ExceptionHandlingController.class.getName(), ex);
        return ResponseEntity.status(status)
                .contentType(CONTENT_TYPE)
                .body(createErrorResponseBody(ex));
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
