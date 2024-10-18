package ru.otus.hw.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.otus.hw.exceptions.NotFoundException;

@Slf4j
@ControllerAdvice
public class ExceptionHandlingController {

    @ExceptionHandler(NotFoundException.class)
    public String notFoundExceptionHandler() {
        return "error/404";
    }

    @ExceptionHandler(Exception.class)
    public String exceptionHandler(Exception ex) {
        log.error("Error: [{}]", ex.getMessage(), ex);
        return "error/500";
    }
}
