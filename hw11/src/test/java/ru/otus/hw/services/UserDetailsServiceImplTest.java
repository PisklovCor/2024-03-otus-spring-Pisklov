package ru.otus.hw.services;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw.mappers.*;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Сервис для управления пользовательскими атрибутами ")
@DataJpaTest
@Import({UserDetailsServiceImpl.class, UserServiceImpl.class,  UserMapper.class, RoleMapper.class})
@Transactional(propagation = Propagation.NEVER)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserDetailsServiceImplTest {

    private static final String USER_TEST_LOGIN = "user_test";
    private static final String ROLE_USER = "ROLE_USER";

    @Autowired
    private UserDetailsServiceImpl service;

    @Test
    void createUser() {
    }

    @Test
    void updateUser() {
    }

    @Test
    void deleteUser() {
    }

    @Test
    void changePassword() {
    }

    @DisplayName("должен проверить наличия пользователя")
    @Test
    @Order(5)
    void userExists() {
        var result = service.userExists(USER_TEST_LOGIN);
        assertThat(result).isEqualTo(true);
    }

    @DisplayName("должен проверить отсутствие пользователя")
    @Test
    @Order(6)
    void userNotExists() {
        var result = service.userExists(USER_TEST_LOGIN + "_1");
        assertThat(result).isEqualTo(false);
    }

    @DisplayName("должен вернуть пользователя по логину")
    @Test
    @Order(7)
    void loadUserByUsername() {
        var userDetails = service.loadUserByUsername(USER_TEST_LOGIN);
        assertThat(userDetails).isNotNull();
        assertThat(userDetails.getUsername()).isEqualTo(USER_TEST_LOGIN);
        assertThat(userDetails.getAuthorities().stream().findFirst().get().toString()).isEqualTo(ROLE_USER);
    }
}