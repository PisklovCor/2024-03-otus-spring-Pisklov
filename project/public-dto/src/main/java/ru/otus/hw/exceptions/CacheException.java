package ru.otus.hw.exceptions;

public class CacheException extends RuntimeException {

    /**
     * Exception creation constructor.
     *
     * @param message error messages
     */
    public CacheException(final String message) {
        super(message);
    }

}
