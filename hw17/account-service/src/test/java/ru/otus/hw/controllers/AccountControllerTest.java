package ru.otus.hw.controllers;

import com.google.gson.Gson;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.hw.SpringBootApplicationTest;
import ru.otus.hw.services.AccountService;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Контроллер аккаунтов ")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AccountControllerTest extends SpringBootApplicationTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private AccountService service;

    private Gson gson;

    @BeforeEach
    void setUp() {
        gson = new Gson();
    }

    @Test
    void getListAccount() {
    }

    @Test
    void gteAccountByLogin() {
    }

}