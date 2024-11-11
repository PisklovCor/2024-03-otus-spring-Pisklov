package ru.otus.hw.exceptions;

public class NotFoundException extends RuntimeException {

    /**
     * Exception creation constructor.
     *
     * @param message error messages
     */
    public NotFoundException(final String message) {
        super(message);
    }
}
