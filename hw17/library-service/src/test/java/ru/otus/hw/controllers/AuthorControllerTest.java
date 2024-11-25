package ru.otus.hw.controllers;

import com.google.gson.Gson;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.hw.SpringBootApplicationTest;
import ru.otus.hw.dto.library.AuthorDto;
import ru.otus.hw.exceptions.NotFoundException;
import ru.otus.hw.services.AuthorService;

import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("Контроллер авторов ")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AuthorControllerTest extends SpringBootApplicationTest {

    private static final MediaType CONTENT_TYPE = new MediaType(APPLICATION_JSON);

    @Autowired
    private MockMvc mvc;

    @MockBean
    private AuthorService authorService;

    @DisplayName("должен вернуть список всех авторов")
    @Order(1)
    @Test
    void getListAuthor() throws Exception {

        List<AuthorDto> authorDtoList = List.of(new AuthorDto());
        given(authorService.findAll()).willReturn(authorDtoList);

        Gson gson = new Gson();
        String resultJson = gson.toJson(authorDtoList);

        mvc.perform(get("/api/v1/author"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(content().json(resultJson));

    }

    @DisplayName("должен вернуть ошибку NOT_FOUND")
    @Order(2)
    @Test
    void getListAuthorException() throws Exception {

        given(authorService.findAll()).willThrow(NotFoundException.class);

        mvc.perform(get("/api/v1/author"))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(CONTENT_TYPE));
    }
}