package ru.otus.hw.configuration;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Data
@Validated
@ConfigurationProperties(prefix = "application")
public class PropertiesConfiguration {

    @NotEmpty
    private List<ApiRoute> apiRoutes;

    @Data
    public static class ApiRoute {

        @NotBlank
        private String id;

        @NotBlank
        private String from;

        @NotBlank
        private String to;
    }
}
