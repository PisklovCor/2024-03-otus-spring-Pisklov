package ru.otus.hw.integration.controllers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import ru.otus.hw.SpringBootApplicationTest;
import ru.otus.hw.dto.AuthorDto;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Контроллер авторов ")
class AuthorControllerTest extends SpringBootApplicationTest {

    private static final int EXPECTED_NUMBER_OF_AUTHOR = 3;

    @DisplayName("должен вернуть список всех авторов")
    @Test
    void getListAuthor() {

        var result = client.get().uri("/api/v1/author")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .returnResult(AuthorDto.class)
                .getResponseBody()
                .collectList()
                .block();

        assertThat(result).hasSize(EXPECTED_NUMBER_OF_AUTHOR);
    }
}