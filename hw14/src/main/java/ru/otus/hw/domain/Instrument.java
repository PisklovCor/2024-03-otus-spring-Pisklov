package ru.otus.hw.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Instrument {

    private UUID instrumentId;

    private InstrumentType instrumentType;

    private long price;

    private long quantity;

    private long nominalValue;

    private String source;

}
