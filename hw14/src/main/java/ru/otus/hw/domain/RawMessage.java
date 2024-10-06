package ru.otus.hw.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class RawMessage {

    @JsonProperty("instrument_type")
    private String instrumentType;

    @JsonProperty("price")
    private long price;

    @JsonProperty("quantity")
    private long quantity;

    @JsonProperty("source")
    private String source;
}
