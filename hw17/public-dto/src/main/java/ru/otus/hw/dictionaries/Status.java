package ru.otus.hw.dictionaries;

import lombok.Generated;

public enum Status {

    CREATED("created", "Order created"),
    WAIT("wait", "The order is in the status of awaiting processing"),
    CANCELED("canceled", "Order cancelled"),
    CONFIRMED("confirmed", "The order has been completed"),
    ERROR("error", "Error while processing order");

    private final String value;

    private String description;

    @Generated
    private Status(final String value, final String description) {
        this.value = value;
        this.description = description;
    }

}
