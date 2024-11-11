package ru.otus.hw.exceptions;

public class ExternalSystemException extends RuntimeException {

    /**
     * Exception creation constructor.
     *
     * @param message error messages
     */
    public ExternalSystemException(final String message) {
        super(message);
    }

}
