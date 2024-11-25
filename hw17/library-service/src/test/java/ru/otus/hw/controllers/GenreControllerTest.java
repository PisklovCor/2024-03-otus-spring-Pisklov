package ru.otus.hw.controllers;

import com.google.gson.Gson;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.hw.SpringBootApplicationTest;
import ru.otus.hw.dto.library.GenreDto;
import ru.otus.hw.services.GenreService;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("Контроллер жанров ")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class GenreControllerTest extends SpringBootApplicationTest {

    private static final MediaType CONTENT_TYPE = new MediaType(APPLICATION_JSON);

    @Autowired
    private MockMvc mvc;

    @MockBean
    private GenreService genreService;

    @DisplayName("должен вернуть список всех жанров")
    @Order(1)
    @Test
    void getListGenre() throws Exception {

        List<GenreDto> genreDtoList = List.of(new GenreDto());
        given(genreService.findAll()).willReturn(genreDtoList);

        Gson gson = new Gson();
        String resultJson = gson.toJson(genreDtoList);

        mvc.perform(get("/api/v1/genre"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(content().json(resultJson));

    }

    @DisplayName("должен вернуть ошибку INTERNAL_SERVER_ERROR")
    @Order(2)
    @Test
    void getListGenreException() throws Exception {

        given(genreService.findAll()).willThrow(RuntimeException.class);

        mvc.perform(get("/api/v1/genre"))
                .andExpect(status().isInternalServerError())
                .andExpect(content().contentType(CONTENT_TYPE));
    }

}