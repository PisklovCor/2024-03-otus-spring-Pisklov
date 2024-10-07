package ru.otus.hw.domain;

import lombok.Getter;

public enum InstrumentType {
    BOND("BOND", "Облигация"),
    SHARE("SHARE", "Акция");

    @Getter
    private String name;

    private String description;

    InstrumentType(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
