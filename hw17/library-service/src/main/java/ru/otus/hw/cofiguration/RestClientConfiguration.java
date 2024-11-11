package ru.otus.hw.cofiguration;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
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
    RestClient orderRestClient() {

        return RestClient.builder()
                .baseUrl(configuration.getOrderUrlBase())
                //.defaultHeader("AUTHORIZATION", fetchToken())
                .build();
    }
}
