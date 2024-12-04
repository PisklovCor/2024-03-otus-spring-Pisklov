package ru.otus.hw.configuration;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Data
@Validated
@ConfigurationProperties(prefix = "application.services")
public class PropertiesConfiguration {

    @NotBlank
    private String accountUrl;

}
