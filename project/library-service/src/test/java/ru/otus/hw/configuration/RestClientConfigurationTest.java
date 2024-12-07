package ru.otus.hw.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestClient;

@TestConfiguration
@RequiredArgsConstructor
@EnableConfigurationProperties(PropertiesConfiguration.class)
public class RestClientConfigurationTest {

    private final PropertiesConfiguration configuration;

    @Bean
    RestClient.Builder orderRestClientBuilder() {
        return RestClient.builder().baseUrl(configuration.getOrderUrl());
    }

    @Bean
    RestClient.Builder accountRestClientBuilder() {
        return RestClient.builder().baseUrl(configuration.getAccountUrl());
    }
}