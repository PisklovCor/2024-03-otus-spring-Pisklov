package ru.otus.hw.services;

import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;
import ru.otus.hw.SpringBootApplicationTest;
import ru.otus.hw.dto.notification.RawMessageCreateDto;
import ru.otus.hw.repositories.RawMessageRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static ru.otus.hw.dictionaries.ExternalSystem.ORDER_SERVICE;
import static ru.otus.hw.dictionaries.MessageType.CREATION;
import static ru.otus.hw.dictionaries.Status.CREATED;

@DisplayName("Сервис для работы c сырыми сообщениями ")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
class RawMessageServiceImplTest extends SpringBootApplicationTest {

    private static final int EXPECTED_QUANTITY_RAW_MESSAGE = 2;

    private static final String USER_LOGIN_TEST = "user_test";

    private static final String CONTENT_TEST = "content";

    @Autowired
    private RawMessageService service;

    @Autowired
    private RawMessageRepository repository;

    @DisplayName("должен загружать список всех сырых сообщений")
    @Sql(scripts = {"/sql/raw_message_insert.sql"}, executionPhase = BEFORE_TEST_METHOD)
    @Test
    void findAll() {

        val resultSet = service.findAll();

        assertThat(resultSet).isNotNull().hasSize(EXPECTED_QUANTITY_RAW_MESSAGE)
                .allMatch(o -> !o.getLogin().isEmpty())
                .allMatch(o -> o.getStatus() != null);
    }

    @DisplayName("должен создать сырое сообщение")
    @Sql(scripts = {"/sql/raw_message_delete.sql"}, executionPhase = AFTER_TEST_METHOD)
    @Test
    void create() {

        val newRawMessage = service.create(RawMessageCreateDto.builder()
                .login(USER_LOGIN_TEST)
                .content(CONTENT_TEST)
                .externalSystemName(ORDER_SERVICE)
                .messageType(CREATION)
                .build());

        val expectedRawMessage = repository.findById(newRawMessage.getId());

        assertTrue(expectedRawMessage.isPresent());
        assertEquals(CONTENT_TEST, expectedRawMessage.get().getContent());
        assertEquals(USER_LOGIN_TEST, expectedRawMessage.get().getLogin());
        assertEquals(CREATED, expectedRawMessage.get().getStatus());
    }
}
