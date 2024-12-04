package ru.otus.hw.dictionaries;

import lombok.Generated;

public enum Status {

    CREATED("created", "Entry created"),
    WAIT("wait", "Awaiting further processing"),
    CANCELED("canceled", "Record processing canceled"),
    CONFIRMED("confirmed", "The entry was processed successfully"),
    ERROR("error", "The entry was processed with an error");

    private final String value;

    private String description;

    @Generated
    private Status(final String value, final String description) {
        this.value = value;
        this.description = description;
    }

}
