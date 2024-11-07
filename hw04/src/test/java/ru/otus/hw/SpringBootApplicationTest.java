package ru.otus.hw;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

/**
 * General class to lift all context.
 */
@SpringBootTest
@TestPropertySource(properties = {"spring.config.location=classpath:application-test.yml"})
public class SpringBootApplicationTest {

}
