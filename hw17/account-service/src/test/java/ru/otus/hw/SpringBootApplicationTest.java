package ru.otus.hw;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import ru.otus.hw.clients.LibraryClient;
import ru.otus.hw.clients.NotificationClient;
import ru.otus.hw.clients.OrderClient;
import ru.otus.hw.producers.RabbitMqProducers;

/**
 * General class for test containers.
 */
@SpringBootTest
@TestPropertySource(properties = {"spring.config.location=classpath:application-test.yml"})
@ContextConfiguration(initializers = {SpringBootApplicationTest.Initializer.class})
public class SpringBootApplicationTest {

    @MockBean
    public LibraryClient libraryClient;

    @MockBean
    public OrderClient orderClient;

    @MockBean
    public NotificationClient notificationClient;

    @MockBean
    public RabbitMqProducers rabbitMqProducers;

    private static final String DATABASE_NAME = "postgres";

    private static final PostgreSQLContainer<?> postgreSQLContainer;

    static {
        postgreSQLContainer = new PostgreSQLContainer<>("postgres:13.3")
                        .withReuse(true)
                        .withDatabaseName(DATABASE_NAME);

        postgreSQLContainer.start();
    }

    static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
            TestPropertyValues.of(
                    "CONTAINER.USERNAME=" + postgreSQLContainer.getUsername(),
                    "CONTAINER.PASSWORD=" + postgreSQLContainer.getPassword(),
                    "CONTAINER.URL=" + postgreSQLContainer.getJdbcUrl()
            ).applyTo(configurableApplicationContext.getEnvironment());
        }
    }
}
