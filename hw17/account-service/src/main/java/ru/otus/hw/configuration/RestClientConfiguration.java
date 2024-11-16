package ru.otus.hw.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.JdkClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

import java.net.http.HttpClient;

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
    RestClient orderRestClient() {

        var client = (HttpClient.newBuilder().version(HttpClient.Version.HTTP_1_1).build());
        var requestFactory = new JdkClientHttpRequestFactory(client);

        return RestClient.builder()
                .requestFactory(requestFactory)
                .baseUrl(configuration.getOrderUrlBase())
                //.defaultHeader("AUTHORIZATION", fetchToken())
                .build();
    }
}
