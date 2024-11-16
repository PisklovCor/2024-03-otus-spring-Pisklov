package ru.otus.hw;

import com.maciejwalkowiak.wiremock.spring.EnableWireMock;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;

/**
 * General class for integration test.
 */
@EnableWireMock
@AutoConfigureWebTestClient(timeout = "600000")
public class BaseIntegrationTest extends SpringBootApplicationTest {

}
