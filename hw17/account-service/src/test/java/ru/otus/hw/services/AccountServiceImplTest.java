package ru.otus.hw.services;

import lombok.val;
import org.hamcrest.collection.IsEmptyCollection;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import ru.otus.hw.SpringBootApplicationTest;
import ru.otus.hw.clients.LibraryClient;
import ru.otus.hw.dto.account.AccountCreateDto;
import ru.otus.hw.dto.account.AccountUpdateDto;
import ru.otus.hw.exceptions.NotFoundException;
import ru.otus.hw.repositories.AccountRepository;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@DisplayName("Сервис для работы с заказакми ")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AccountServiceImplTest extends SpringBootApplicationTest {

    private static final int EXPECTED_NUMBER_OF_ACCOUNT = 1;
    private static final String USER_LOGIN = "user_test";
    private static final String USER_NAME = "user_test_name";
    private static final String NEW_ACCOUNT_NAME = "user_junit_name";
    private static final String NEW_ACCOUNT_SURNAME = "user_junit_surname";
    private static final String NEW_ACCOUNT_LOGIN = "user_junit";
    private static final String NEW_ACCOUNT_MAIL = "user_junit@protonmail.com";
    private static final String ACCOUNT_SURNAME_UPDATE = "user_junit_surname_update";
    private static final String ACCOUNT_MAIL_UPDATE = "user_junit_update@protonmail.com";
    private static final long NEW_ACCOUNT_ID = 2;

    @Autowired
    private AccountService service;

    @Autowired
    private AccountRepository repository;

    @DisplayName("должен загружать список всех аккаунтов")
    @Test
    @Order(1)
    void findAll() {
        var listAccountDto = service.findAll();

        assertThat(listAccountDto).isNotNull().hasSize(EXPECTED_NUMBER_OF_ACCOUNT)
                .allMatch(o -> !o.getLogin().isEmpty())
                .allMatch(o -> o.getLogin().equals(USER_LOGIN));
    }

    @DisplayName("должен загружать аккаунто по логину")
    @Test
    @Order(2)
    void findAllByLogin() {
        var accountDto = service.findAllByLogin(USER_LOGIN);

        assertEquals(USER_NAME, accountDto.getName());
    }

    @DisplayName("должен создать аккаунт")
    @Test
    @Order(3)
    void create() {
        var newAccountDto = service.create(
                new AccountCreateDto(NEW_ACCOUNT_NAME, NEW_ACCOUNT_SURNAME, NEW_ACCOUNT_LOGIN, NEW_ACCOUNT_MAIL));
        var optionalExpectedAccountEntity = repository.findById(newAccountDto.getId());

        assertTrue(optionalExpectedAccountEntity.isPresent());
        assertEquals(NEW_ACCOUNT_NAME, optionalExpectedAccountEntity.get().getName());
        assertEquals(NEW_ACCOUNT_LOGIN, optionalExpectedAccountEntity.get().getLogin());
        assertEquals(NEW_ACCOUNT_MAIL, optionalExpectedAccountEntity.get().getMail());
    }

    @DisplayName("должен обновить аккаунт")
    @Test
    @Order(4)
    void update() {
        service.update(new AccountUpdateDto(NEW_ACCOUNT_ID, NEW_ACCOUNT_NAME, ACCOUNT_SURNAME_UPDATE,
                NEW_ACCOUNT_LOGIN, ACCOUNT_MAIL_UPDATE));
        var optionalExpectedAccountEntity = repository.findById(NEW_ACCOUNT_ID);

        assertTrue(optionalExpectedAccountEntity.isPresent());
        assertEquals(NEW_ACCOUNT_NAME, optionalExpectedAccountEntity.get().getName());
        assertEquals(ACCOUNT_SURNAME_UPDATE, optionalExpectedAccountEntity.get().getSurname());
        assertEquals(NEW_ACCOUNT_LOGIN, optionalExpectedAccountEntity.get().getLogin());
        assertEquals(ACCOUNT_MAIL_UPDATE, optionalExpectedAccountEntity.get().getMail());
    }

    @DisplayName("должен удалять аккаунт по его id")
    @Test
    @Order(5)
    void deleteById() {
        service.deleteById(NEW_ACCOUNT_ID);

        assertThrows(NotFoundException.class, () -> service.findAllByLogin(NEW_ACCOUNT_LOGIN));
    }
}