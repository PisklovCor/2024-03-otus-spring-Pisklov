//package ru.otus.hw.services;
//
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.annotation.DirtiesContext;
//import org.springframework.test.context.jdbc.Sql;
//import ru.otus.hw.SpringBootApplicationTest;
//import ru.otus.hw.repositories.AccountBookRepository;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_CLASS;
//import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
//import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_CLASS;
//import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;
//
//@DisplayName("Сервис для работы со связями аккаунтов с книгами ")
//@Sql(scripts = {"/sql/account_insert.sql"}, executionPhase = BEFORE_TEST_CLASS)
//@Sql(scripts = {"/sql/account_delete.sql"}, executionPhase = AFTER_TEST_CLASS)
//class AccountBookServiceImplTest extends SpringBootApplicationTest {
//
//    private static final int EXPECTED_NUMBER_OF_ACCOUNT = 2;
//
//    private static final String USER_LOGIN = "user_test";
//
//    private static final String USER_NAME = "user_test_name";
//
//    private static final String NEW_ACCOUNT_NAME = "user_junit_name";
//
//    private static final String NEW_ACCOUNT_SURNAME = "user_junit_surname";
//
//    private static final String NEW_ACCOUNT_LOGIN = "user_junit";
//
//    private static final String NEW_ACCOUNT_MAIL = "user_junit@protonmail.com";
//
//    private static final String ACCOUNT_SURNAME_UPDATE = "user_junit_surname_update";
//
//    private static final String ACCOUNT_MAIL_UPDATE = "user_junit_update@protonmail.com";
//
//    @Autowired
//    private AccountBookService service;
//
//    @Autowired
//    private AccountBookRepository repository;
//
//    @DisplayName("должен загружать список всех связей")
//    @Sql(scripts = {"/sql/account_book_insert.sql"}, executionPhase = BEFORE_TEST_METHOD)
//    @Sql(scripts = {"/sql/account_book_delete.sql"}, executionPhase = AFTER_TEST_METHOD)
//    @Test
//    void findAll() {
//        var listAccountDto = service.findAll();
//
//        assertThat(listAccountDto).isNotNull().hasSize(EXPECTED_NUMBER_OF_ACCOUNT);
//
//    }
//
//    @Test
//    void findAllByLogin() {
//    }
//
//    @Test
//    void create() {
//    }
//
//    @Test
//    void update() {
//    }
//
//    @Test
//    void deleteById() {
//    }
//}