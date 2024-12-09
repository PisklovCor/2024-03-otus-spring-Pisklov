package ru.otus.hw;

import com.maciejwalkowiak.wiremock.spring.EnableWireMock;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.RestClient;
import ru.otus.hw.configuration.PropertiesConfiguration;
import ru.otus.hw.configuration.RestClientConfigurationTest;

/**
 * General class for integration test.
 */
@Import(RestClientConfigurationTest.class)
@EnableWireMock
@ActiveProfiles("test")
@AutoConfigureWebTestClient(timeout = "600000")
public class BaseIntegrationTest extends SpringBootApplicationTest {

//    @Bean
//    RestClient.Builder orderRestClientBuilder() {
//        return RestClient.builder().baseUrl("121321");
//    }
//
//    @Bean
//    RestClient.Builder accountRestClientBuilder() {
//        return RestClient.builder().baseUrl("dasdsadas");
//    }

}
