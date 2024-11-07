package ru.otus.hw;


import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

/**
 * General class to lift all context.
 */
@SpringBootTest
@AutoConfigureTestEntityManager
@AutoConfigureMockMvc
@TestPropertySource(properties = {"spring.config.location=classpath:application.yml"})
public class SpringBootApplicationTest {

}
