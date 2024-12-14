package ru.otus.hw.integration.controllers;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.hw.controllers.BookController;
import ru.otus.hw.dto.BookCreateDto;
import ru.otus.hw.dto.BookDto;
import ru.otus.hw.dto.BookUpdateDto;
import ru.otus.hw.mappers.AuthorMapper;
import ru.otus.hw.mappers.BookMapper;
import ru.otus.hw.mappers.GenreMapper;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Genre;
import ru.otus.hw.repositories.BookRepository;
import ru.otus.hw.services.DataAcquisitionService;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@Import({BookMapper.class, AuthorMapper.class, GenreMapper.class})
@WebFluxTest(BookController.class)
@DisplayName("Реактивный контроллер книг ")
class BookControllerWebFluxTest {

    @Autowired
    public WebTestClient client;

    @Autowired
    public BookMapper mapper;

    @MockBean
    private BookRepository repository;

    @MockBean
    private DataAcquisitionService data;

    private static final String TEST_TITLE = "Software testing";

    private static final String TEST_TITLE_UPDATE = "It was almost successful";

    private static final int EXPECTED_BOOK_COUNT = 1;

    private static Book mockBook;

    @BeforeAll
    public static void init(){
        mockBook = createMockBook();
    }

    @DisplayName("должен вернуть список всех книг")
    @Test
    void getListBook() {

        when(repository.findAll()).thenReturn(Flux.fromIterable(List.of(mockBook)));

        var result = client.get().uri("/api/v1/book")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .returnResult(BookDto.class)
                .getResponseBody()
                .collectList()
                .block();

        assertThat(result).isNotNull();
        assertThat(result).hasSize(EXPECTED_BOOK_COUNT);
    }

    @DisplayName("должен создать новую книгу")
    @Test
    void createBook() {

        var dto = new BookCreateDto();
        dto.setTitle(mockBook.getTitle());
        dto.setAuthorId(mockBook.getAuthor().getId());
        dto.setGenresId(mockBook.getGenres().stream().map(Genre::getId).toList());

        when(repository.save(any())).thenReturn(Mono.just(mockBook));
        when(data.findMonoAuthorById(any())).thenReturn(Mono.just(mockBook.getAuthor()));
        when(data.findAllMonoGenreByIds(any())).thenReturn(Mono.just(mockBook.getGenres()));

        var result = client.post().uri("/api/v1/book")
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(dto), BookCreateDto.class)
                .exchange()
                .expectStatus().isCreated()
                .returnResult(BookDto.class)
                .getResponseBody()
                .blockFirst();

        assertThat(result).isNotNull();
        assertThat(result.getTitle()).isEqualTo(TEST_TITLE);
    }

    @DisplayName("должен обновить существующую книгу")
    @Test
    void updateBook() {

        var dto = new BookUpdateDto();
        dto.setId(mockBook.getId());
        dto.setTitle(TEST_TITLE_UPDATE);
        dto.setAuthorId(mockBook.getAuthor().getId());
        dto.setGenresId(mockBook.getGenres().stream().map(Genre::getId).toList());

        var mockBookUpdate = createMockBook();
        mockBookUpdate.setTitle(TEST_TITLE_UPDATE);

        when(repository.findById(mockBook.getId())).thenReturn(Mono.just(mockBook));
        when(repository.save(any())).thenReturn(Mono.just(mockBookUpdate));
        when(data.findMonoAuthorById(any())).thenReturn(Mono.just(mockBook.getAuthor()));
        when(data.findAllMonoGenreByIds(any())).thenReturn(Mono.just(mockBook.getGenres()));

        var result = client.put().uri("/api/v1/book")
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(dto), BookCreateDto.class)
                .exchange()
                .expectStatus().isAccepted()
                .returnResult(BookDto.class)
                .getResponseBody()
                .blockFirst();

        assertThat(result).isNotNull();
        assertThat(result.getTitle()).isNotEqualTo(mockBook.getTitle());
        assertThat(result.getTitle()).isEqualTo(TEST_TITLE_UPDATE);
    }

    @DisplayName("должен удалить книгу")
    @Test
    void deleteBook() {

        client.delete().uri("/api/v1/book/{bookId}", mockBook.getId())
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isNoContent();

        verify(repository).deleteById(mockBook.getId());
    }

    private static Book createMockBook() {
        return new Book("e23e24df-5fb9-4367-abf7-96ba6e5a09ba", TEST_TITLE, createMockAuthor(), createMockGenres());
    }

    private static Author createMockAuthor() {
        return new Author("5aaeef03-73b6-4926-9830-b94d08dfad5a", "name_1");
    }

    private static List<Genre> createMockGenres() {
        List<Genre> genreList = new ArrayList<>();
        genreList.add(new Genre("5aaeef03-73b6-4926-9830-b94d08dfad5a", "name_1"));
        genreList.add(new Genre("2667b280-d876-4a8f-9e90-1d38e1e47019", "name_2"));
        return genreList;
    }
}