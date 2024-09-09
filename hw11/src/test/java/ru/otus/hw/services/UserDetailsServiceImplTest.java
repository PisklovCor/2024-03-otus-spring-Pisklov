package ru.otus.hw.services;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw.mappers.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Сервис для управления пользовательскими атрибутами ")
@DataJpaTest
@Import({UserDetailsServiceImpl.class, UserServiceImpl.class,  UserMapper.class, RoleMapper.class})
@Transactional(propagation = Propagation.NEVER)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserDetailsServiceImplTest {

    private static final String USER_TEST_LOGIN = "user_test";
    private static final String USER_TEST_PASSWORD = "user_test";
    private static final String USER_TEST_ROLE = "USER";
    private static final String NEW_USER_TEST_LOGIN = "user_test_new";
    private static final String NEW_USER_TEST_PASSWORD = "test_user";

    private static UserDetails userDetails;

    @Autowired
    private UserDetailsServiceImpl service;

    @BeforeAll
    static void init() {

        userDetails = User.builder()
                .username(NEW_USER_TEST_LOGIN)
                .password(USER_TEST_PASSWORD)
                .roles(USER_TEST_ROLE)
                .build();
    }

    @DisplayName("должен создать нового пользователя")
    @Test
    @Order(1)
    void createUser() {

        service.createUser(userDetails);
        var result = service.userExists(NEW_USER_TEST_LOGIN);
        assertThat(result).isEqualTo(true);
    }

    @DisplayName("должен обновить существующего пользователя")
    @Test
    @Order(2)
    void updateUser() {

        service.updateUser(userDetails);
        var result = service.userExists(NEW_USER_TEST_LOGIN);
        assertThat(result).isEqualTo(true);
    }

    @DisplayName("должен удалить пользователя по логину")
    @Test
    @Order(3)
    void deleteUser() {
        service.deleteUser(NEW_USER_TEST_LOGIN);
        var result = service.userExists(NEW_USER_TEST_LOGIN);
        assertThat(result).isEqualTo(false);
    }

    @WithMockUser(
            username = "user_test",
            authorities = {"ROLE_USER"}
    )
    @DisplayName("должен поменять пароль текущего пользователя")
    @Test
    @Order(4)
    void changePassword() {
        service.changePassword(USER_TEST_PASSWORD, NEW_USER_TEST_PASSWORD);
        var userDetails = service.loadUserByUsername(USER_TEST_LOGIN);

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        final boolean isPasswordMatches = passwordEncoder.matches(NEW_USER_TEST_PASSWORD, userDetails.getPassword());

        assertThat(userDetails).isNotNull();
        assertTrue(isPasswordMatches);
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
        var result = service.userExists(NEW_USER_TEST_LOGIN);
        assertThat(result).isEqualTo(false);
    }

    @DisplayName("должен вернуть пользователя по логину")
    @Test
    @Order(7)
    void loadUserByUsername() {
        var userDetails = service.loadUserByUsername(USER_TEST_LOGIN);
        assertThat(userDetails).isNotNull();
        assertThat(userDetails.getUsername()).isEqualTo(USER_TEST_LOGIN);
        assertThat(userDetails.getAuthorities().stream().findFirst().get().toString())
                .isEqualTo("ROLE_" + USER_TEST_ROLE);
    }
}