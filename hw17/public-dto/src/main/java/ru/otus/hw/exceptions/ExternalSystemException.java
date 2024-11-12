package ru.otus.hw.exceptions;

import lombok.Getter;
import ru.otus.hw.dictionaries.ExternalSystem;

@Getter
public class ExternalSystemException extends RuntimeException {

    private final String externalSystem;

    /**
     * Exception creation constructor.
     *
     * @param message error messages
     */
    public ExternalSystemException(final String message, ExternalSystem externalSystem) {
        super(message);
        this.externalSystem = externalSystem.name();
    }
}
