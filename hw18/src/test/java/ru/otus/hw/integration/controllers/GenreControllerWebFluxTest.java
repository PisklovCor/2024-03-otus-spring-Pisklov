package ru.otus.hw.integration.controllers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import ru.otus.hw.controllers.GenreController;
import ru.otus.hw.mappers.GenreMapper;
import ru.otus.hw.models.Genre;
import ru.otus.hw.repositories.GenreRepository;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@Import(GenreMapper.class)
@WebFluxTest(GenreController.class)
@DisplayName("Реактивный контроллер жанров ")
class GenreControllerWebFluxTest {

    @Autowired
    public WebTestClient client;

    @MockBean
    private GenreRepository repository;

    private static final int EXPECTED_NUMBER_OF_GENRE = 6;

    @DisplayName("должен вернуть список всех жанров")
    @Test
    void getListGenre() {

        when(repository.findAll()).thenReturn(Flux.fromIterable(createMockGenres()));

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

    private List<Genre> createMockGenres() {
        List<Genre> genreList = new ArrayList<>();

        genreList.add(new Genre("5aaeef03-73b6-4926-9830-b94d08dfad5a", "name_1"));
        genreList.add(new Genre("2667b280-d876-4a8f-9e90-1d38e1e47019", "name_2"));
        genreList.add(new Genre("7c91ad84-8ff7-45a2-82a8-b60ee356e4d9", "name_3"));
        genreList.add(new Genre("9504123f-5a6a-4f3d-b4bf-905ba121a314", "name_4"));
        genreList.add(new Genre("06d3fa96-c481-443d-a95a-e753fff89bb3", "name_5"));
        genreList.add(new Genre("82fe0c1f-b529-490f-83d6-35775fcee3f1", "name_6"));

        return genreList;
    }
}
