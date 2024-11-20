package ru.otus.hw.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

/**
 * За основу взято <a href="https://howtodoinjava.com/spring/spring-restclient/">...</a>
 */
@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties(PropertiesConfiguration.class)
public class RestClientConfiguration {

   private final PropertiesConfiguration configuration;

    @Bean
    RestClient libraryRestClient() {

        return RestClient.builder()
                .baseUrl(configuration.getLibraryUrlBase())
                //.defaultHeader("AUTHORIZATION", fetchToken())
                .build();
    }

    @Bean
    @LoadBalanced
    RestClient.Builder orderRestClientBuilder() {
        return RestClient.builder().baseUrl(configuration.getOrderUrlBase());
    }

    @Bean
    @LoadBalanced
    RestClient.Builder notificationRestClientBuilder() {
        return RestClient.builder().baseUrl(configuration.getNotificationUrlBase());
    }
}
