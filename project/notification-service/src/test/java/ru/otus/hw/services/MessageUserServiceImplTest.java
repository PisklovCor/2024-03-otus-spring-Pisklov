package ru.otus.hw.services;

import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;
import ru.otus.hw.SpringBootApplicationTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;
import static ru.otus.hw.dictionaries.Status.WAIT;

@DisplayName("Сервис для работы с сообщениями ")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
class MessageUserServiceImplTest extends SpringBootApplicationTest {

    private static final int EXPECTED_QUANTITY_MESSAGE_USER_ALL = 3;

    private static final int EXPECTED_QUANTITY_MESSAGE_USER_BY_LOGIN = 2;

    private static final int EXPECTED_QUANTITY_MESSAGE_USER_BY_STATUS = 1;

    @Autowired
    private MessageUserService service;

    @DisplayName("должен загружать список всех сообщений")
    @Sql(scripts = {"/sql/message_to_user_insert.sql"}, executionPhase = BEFORE_TEST_METHOD)
    @Sql(scripts = {"/sql/message_to_user_delete.sql"}, executionPhase = AFTER_TEST_METHOD)
    @Test
    void findAll() {

        val resultSet = service.findAll();

        assertThat(resultSet).isNotNull().hasSize(EXPECTED_QUANTITY_MESSAGE_USER_ALL)
                .allMatch(o -> !o.getLogin().isEmpty())
                .allMatch(o -> o.getStatus() != null);
    }

    @DisplayName("должен загружать список всех сообщений по логину пользоваетля")
    @Sql(scripts = {"/sql/message_to_user_insert.sql"}, executionPhase = BEFORE_TEST_METHOD)
    @Sql(scripts = {"/sql/message_to_user_delete.sql"}, executionPhase = AFTER_TEST_METHOD)
    @Test
    void findAllByLogin() {

        val resultSet = service.findAllByLogin("user_test");

        assertThat(resultSet).isNotNull().hasSize(EXPECTED_QUANTITY_MESSAGE_USER_BY_LOGIN)
                .allMatch(o -> !o.getLogin().isEmpty())
                .allMatch(o -> o.getStatus() != null);
    }

    @DisplayName("должен загружать список всех сообщений по статусу")
    @Sql(scripts = {"/sql/message_to_user_insert.sql"}, executionPhase = BEFORE_TEST_METHOD)
    @Sql(scripts = {"/sql/message_to_user_delete.sql"}, executionPhase = AFTER_TEST_METHOD)
    @Test
    void findAllByStatus() {

        val resultSet = service.findAllByStatus(WAIT);

        assertThat(resultSet).isNotNull().hasSize(EXPECTED_QUANTITY_MESSAGE_USER_BY_STATUS)
                .allMatch(o -> !o.getLogin().isEmpty())
                .allMatch(o -> o.getStatus() != null);
    }
}