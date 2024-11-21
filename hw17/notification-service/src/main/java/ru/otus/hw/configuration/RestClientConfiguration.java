package ru.otus.hw.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

/**
 * За основу взято: <a href="https://howtodoinjava.com/spring/spring-restclient/">...</a>
 * LoadBalanced:
 * <a href="https://docs.spring.io/spring-cloud-commons/reference/spring-cloud-commons/loadbalancer.html">...</a>
 */
@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties(PropertiesConfiguration.class)
public class RestClientConfiguration {

    private final PropertiesConfiguration configuration;

    @Bean
    @LoadBalanced
    RestClient.Builder accountRestClientBuilder() {
        return RestClient.builder().baseUrl(configuration.getAccountUrl());
    }
}
