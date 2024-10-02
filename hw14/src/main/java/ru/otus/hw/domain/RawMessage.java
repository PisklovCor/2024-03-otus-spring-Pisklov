package ru.otus.hw.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RawMessage {

    private String instrumentType;

    private long price;

    private long quantity;

    private String source;
}
