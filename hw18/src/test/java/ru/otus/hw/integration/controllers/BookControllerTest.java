package ru.otus.hw.integration.controllers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import reactor.core.publisher.Mono;
import ru.otus.hw.SpringBootApplicationTest;
import ru.otus.hw.dto.BookCreateDto;
import ru.otus.hw.dto.BookDto;
import ru.otus.hw.dto.BookUpdateDto;
import ru.otus.hw.dto.GenreDto;
import ru.otus.hw.mappers.BookMapper;
import ru.otus.hw.repositories.BookRepository;

import java.util.List;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Контроллер книг ")
class BookControllerTest extends SpringBootApplicationTest {

    private static final String TEST_TITLE = "Software testing";

    private static final String TEST_TITLE_UPDATE = "It was almost successful";

    private static final int NUMBER_OF_BOOKS_IN_TESTS = 1;

    @Autowired
    private BookRepository repository;

    @Autowired
    private BookMapper mapper;

    @DisplayName("должен вернуть список всех авторов")
    @Test
    void getListBook() {

        var expectedBookCount = getListBooksFromRepository().size();

        var result = client.get().uri("/api/v1/book")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .returnResult(BookDto.class)
                .getResponseBody()
                .collectList()
                .block();

        assertThat(result).isNotNull();
        assertThat(result).hasSize(expectedBookCount);
    }

    @DisplayName("должен создать новую книгу")
    @Test
    void createBook() {

        var expectedBookCount = getListBooksFromRepository().size();
        var belovedBook = getFirstBooksFromRepository();

        var dto = new BookCreateDto();
        dto.setTitle(TEST_TITLE);
        dto.setAuthorId(belovedBook.getAuthor().getId());
        dto.setGenresId(belovedBook.getGenres().stream().map(GenreDto::getId).toList());

        var result = client.post().uri("/api/v1/book")
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(dto), BookCreateDto.class)
                .exchange()
                .expectStatus().isCreated()
                .returnResult(BookDto.class)
                .getResponseBody()
                .blockFirst();

        var expectedDtoListAfterCreation = getListBooksFromRepository();

        assertThat(result).isNotNull();
        assertThat(expectedDtoListAfterCreation).hasSize(expectedBookCount + NUMBER_OF_BOOKS_IN_TESTS);
        assertThat(expectedDtoListAfterCreation).contains(result);
        assertThat(result.getTitle()).isEqualTo(TEST_TITLE);
    }

    @DisplayName("должен обновить существующую книгу")
    @Test
    void updateBook() {

        var belovedBook = getFirstBooksFromRepository();
        var exampleTitle = belovedBook.getTitle();

        var dto = new BookUpdateDto();
        dto.setId(belovedBook.getId());
        dto.setTitle(TEST_TITLE_UPDATE);
        dto.setAuthorId(belovedBook.getAuthor().getId());
        dto.setGenresId(belovedBook.getGenres().stream().map(GenreDto::getId).toList());

        var result = client.put().uri("/api/v1/book")
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(dto), BookCreateDto.class)
                .exchange()
                .expectStatus().isAccepted()
                .returnResult(BookDto.class)
                .getResponseBody()
                .blockFirst();

        assertThat(result).isNotNull();
        assertThat(result.getTitle()).isNotEqualTo(exampleTitle);
        assertThat(result.getTitle()).isEqualTo(TEST_TITLE_UPDATE);
    }

    @DisplayName("должен удалить книгу")
    @Test
    void deleteBook() {

        var expectedBookCount = getListBooksFromRepository().size();
        var belovedBook = getFirstBooksFromRepository();

        client.delete().uri("/api/v1/book/{bookId}", belovedBook.getId())
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isNoContent();

        var expectedDtoListAfterRemoval = getListBooksFromRepository();

        assertThat(expectedDtoListAfterRemoval).hasSize(expectedBookCount - NUMBER_OF_BOOKS_IN_TESTS);
        assertThat(expectedDtoListAfterRemoval).doesNotContain(belovedBook);

    }

    private BookDto getFirstBooksFromRepository() {
        return getListBooksFromRepository().get(0);
    }

    private List<BookDto> getListBooksFromRepository() {
        return Objects.requireNonNull(repository.findAll().map(mapper::toDto).collectList().block());
    }
}