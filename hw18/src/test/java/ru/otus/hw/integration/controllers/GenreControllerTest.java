package ru.otus.hw.integration.controllers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import ru.otus.hw.SpringBootApplicationTest;
import ru.otus.hw.models.Genre;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Контроллер жанров ")
class GenreControllerTest extends SpringBootApplicationTest {

    private static final int EXPECTED_NUMBER_OF_GENRE = 6;

    @Autowired
    private WebTestClient client;

    @DisplayName("должен вернуть список всех жанров")
    @Test
    void getListGenre() {

        var result = client.get().uri("/api/v1/genre")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .returnResult(Genre.class)
                .getResponseBody()
                .collectList()
                .block();

        assertThat(result).hasSize(EXPECTED_NUMBER_OF_GENRE);
    }
}