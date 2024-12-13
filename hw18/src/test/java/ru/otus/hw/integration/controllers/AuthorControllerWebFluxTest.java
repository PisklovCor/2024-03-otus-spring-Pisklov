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
import ru.otus.hw.controllers.AuthorController;
import ru.otus.hw.dto.AuthorDto;
import ru.otus.hw.mappers.AuthorMapper;
import ru.otus.hw.models.Author;
import ru.otus.hw.repositories.AuthorRepository;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@Import(AuthorMapper.class)
@WebFluxTest(AuthorController.class)
@DisplayName("Реактивный контроллер авторов ")
class AuthorControllerWebFluxTest {

    @Autowired
    public WebTestClient client;

    @MockBean
    private AuthorRepository repository;

    private static final int EXPECTED_NUMBER_OF_AUTHOR = 3;

    @DisplayName("должен вернуть список всех авторов")
    @Test
    void getListAuthor() {

        when(repository.findAll()).thenReturn(Flux.fromIterable(createMockAuthors()));

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

    private List<Author> createMockAuthors() {
        List<Author> authorList = new ArrayList<>();

        authorList.add(new Author("5aaeef03-73b6-4926-9830-b94d08dfad5a", "name_1"));
        authorList.add(new Author("2667b280-d876-4a8f-9e90-1d38e1e47019", "name_2"));
        authorList.add(new Author("7c91ad84-8ff7-45a2-82a8-b60ee356e4d9", "name_3"));

        return authorList;
    }
}
