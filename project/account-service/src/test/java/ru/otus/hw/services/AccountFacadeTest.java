package ru.otus.hw.services;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import ru.otus.hw.SpringBootApplicationTest;
import ru.otus.hw.dto.library.CommentDto;
import ru.otus.hw.dto.notification.MessageUserDto;
import ru.otus.hw.dto.order.OrderDto;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_CLASS;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_CLASS;
import static ru.otus.hw.dictionaries.Status.WAIT;

@DisplayName("Фасад для работы с аккаунтом ")
@Sql(scripts = {"/sql/account_insert.sql"}, executionPhase = BEFORE_TEST_CLASS)
@Sql(scripts = {"/sql/account_delete.sql"}, executionPhase = AFTER_TEST_CLASS)
class AccountFacadeTest extends SpringBootApplicationTest {

    private static final String USER_LOGIN = "user_test";

    private static final String MOCK_STRING = "mock_string";

    private static final long MOCK_ID = 14L;

    private static final int EXPECTED_NUMBER_COUNT = 1;

    @Autowired
    private AccountFacade facade;

    @DisplayName("должен загружать список заказов по логину")
    @Test
    void getAllOrderByLogin() {

        var orderDto = OrderDto.builder()
                .id(MOCK_ID)
                .login(USER_LOGIN)
                .bookTitle(MOCK_STRING)
                .status(WAIT)
                .build();

        given(orderClient.getOrderByLogin(USER_LOGIN)).willReturn(List.of(orderDto));

        var result = facade.getAllOrderByLogin(USER_LOGIN);

        assertThat(result).isNotNull().hasSize(EXPECTED_NUMBER_COUNT)
                .allMatch(r -> !r.getLogin().isEmpty())
                .allMatch(r -> r.getLogin().equals(USER_LOGIN))
                .allMatch(r -> r.getStatus().equals(WAIT));
    }

    @DisplayName("должен загружать список уведомлений по логину")
    @Test
    void getAllNotificationByLogin() {

        var messageUserDto = MessageUserDto.builder()
                .id(MOCK_ID)
                .login(USER_LOGIN)
                .message(MOCK_STRING)
                .status(WAIT)
                .build();

        given(notificationClient.getAllMessageUserByLogin(USER_LOGIN)).willReturn(List.of(messageUserDto));

        var result = facade.getAllNotificationByLogin(USER_LOGIN);

        assertThat(result).isNotNull().hasSize(EXPECTED_NUMBER_COUNT)
                .allMatch(r -> !r.getLogin().isEmpty())
                .allMatch(r -> r.getLogin().equals(USER_LOGIN))
                .allMatch(r -> r.getStatus().equals(WAIT));
    }

    @DisplayName("должен загружать список комментариев по логину")
    @Test
    void getAllCommentByLogin() {

        var commentDto = CommentDto.builder()
                .id(MOCK_ID)
                .login(USER_LOGIN)
                .content(MOCK_STRING)
                .build();

        given(libraryClient.getCommentByUserLogin(USER_LOGIN)).willReturn(List.of(commentDto));

        var result = facade.getAllCommentByLogin(USER_LOGIN);

        assertThat(result).isNotNull().hasSize(EXPECTED_NUMBER_COUNT)
                .allMatch(r -> !r.getLogin().isEmpty())
                .allMatch(r -> r.getLogin().equals(USER_LOGIN))
                .allMatch(r -> r.getContent().equals(MOCK_STRING));
    }
}