package ru.otus.hw.integration.endpoints.functional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import ru.otus.hw.SpringBootApplicationTest;
import ru.otus.hw.dto.BookDto;
import ru.otus.hw.mappers.BookMapper;
import ru.otus.hw.repositories.BookRepository;

import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Функциональный эндпоин книг ")
public class BookFunctionalEndpointTest extends SpringBootApplicationTest {

    @Autowired
    private WebTestClient client;

    @Autowired
    private BookRepository repository;

    @Autowired
    private BookMapper mapper;

    @DisplayName("должен вернуть книгу по ID")
    @Test
    void gteBookById() {

        var book = repository.findAll();
        var expectedDto = Objects.requireNonNull(book.map(mapper::toDto).collectList().block()).get(0);

        var result = client.get().uri("/route/v1/book/{bookId}", expectedDto.getId())
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .returnResult(BookDto.class)
                .getResponseBody()
                .blockFirst();

       assertThat(result).isEqualTo(expectedDto);
    }
}
