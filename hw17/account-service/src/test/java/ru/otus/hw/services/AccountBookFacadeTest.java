package ru.otus.hw.services;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import ru.otus.hw.SpringBootApplicationTest;
import ru.otus.hw.dto.library.BookDto;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_CLASS;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_CLASS;

@DisplayName("Фасад для работы со связями аккаунтов с книгами ")
@Sql(scripts = {"/sql/account_insert.sql"}, executionPhase = BEFORE_TEST_CLASS)
@Sql(scripts = {"/sql/account_book_insert.sql"}, executionPhase = BEFORE_TEST_CLASS)
@Sql(scripts = {"/sql/account_delete.sql"}, executionPhase = AFTER_TEST_CLASS)
@Sql(scripts = {"/sql/account_book_delete.sql"}, executionPhase = AFTER_TEST_CLASS)
class AccountBookFacadeTest extends SpringBootApplicationTest {

    private static final long MOCK_ID = 14L;

    private static final String MOCK_STRING = "mock_string";

    private static final int EXPECTED_NUMBER_COUNT = 2;

    @Autowired
    private AccountBookFacade facade;

    @DisplayName("должен загружать список всз связей")
    @Test
    void findAll() {

        var bookDto = BookDto.builder()
                .id(MOCK_ID)
                .title(MOCK_STRING)
                .build();

        given(libraryClient.getBookById(anyLong())).willReturn(bookDto);

        var result = facade.findAll();

        assertThat(result).isNotNull().hasSize(EXPECTED_NUMBER_COUNT)
                .allMatch(r -> r.getId() > 0)
                .allMatch(r -> r.getBook().getId() == MOCK_ID)
                .allMatch(r -> r.getBook().getTitle().equals(MOCK_STRING));
    }

    @Test
    void findAllByLogin() {
    }

    @Test
    void create() {
    }

    @Test
    void update() {
    }
}