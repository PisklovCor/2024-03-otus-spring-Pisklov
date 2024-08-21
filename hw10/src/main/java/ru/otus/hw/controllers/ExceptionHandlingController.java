package ru.otus.hw.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.otus.hw.exceptions.NotFoundException;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@Slf4j
@RestControllerAdvice
public class ExceptionHandlingController {


    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(BAD_REQUEST)
    public String methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException ex) {
        log.error("Error: [{}]", ex.getMessage());
        return "Error forming request";
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(NOT_FOUND)
    public String notFoundExceptionHandler(NotFoundException ex) {
        log.error("Error: [{}]", ex.getMessage());
        return "An error occurred while searching for the object";
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    public String exceptionHandler(Exception ex) {
        log.error("Error: [{}]", ex.getMessage());
        return "An unexpected error occurred";
    }
}
