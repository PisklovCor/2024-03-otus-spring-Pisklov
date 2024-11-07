package ru.otus.hw.integration;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.hw.SpringBootApplicationTest;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DisplayName("Интеграционный тест использования Spring ACL ")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class SpringAclSecurityTest extends SpringBootApplicationTest {

    private static final String USER_TEST_LOGIN = "user_test";

    private static final String USER_TEST_PASSWORD = "$2a$12$2tcN8kkCE7dcxyXNB9nTV.hKgNPBXIcHfplo0nynixi2BqVsseX1q";

    private static final String USER_TEST_ROLE = "USER";

    @Autowired
    private MockMvc mvc;

    private static UserDetails userDetails;

    @BeforeAll
    static void init() {

        userDetails = User.builder()
                .username(USER_TEST_LOGIN)
                .password(USER_TEST_PASSWORD)
                .roles(USER_TEST_ROLE)
                .build();
    }

    @DisplayName("должен вернуть страницу списка с одной книгой для авторизованного пользователя")
    @Test
    void listBook() throws Exception {

        mvc.perform(get("/book").with(user(userDetails)))
                .andExpect(status().isOk())
                .andExpect(model().attribute("books",  hasSize(1)))
                .andExpect(view().name("list"));
    }
}