package ru.otus.hw.services;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import ru.otus.hw.AbstractRepositoryTest;
import ru.otus.hw.converters.AuthorConverter;
import ru.otus.hw.converters.BookConverter;
import ru.otus.hw.converters.GenreConverter;
import ru.otus.hw.models.Genre;
import ru.otus.hw.repositories.BookRepository;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Сервис для работы с книгами ")
@Import({BookConverter.class, AuthorConverter.class, GenreConverter.class, BookServiceImpl.class})
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class BookServiceImplTest extends AbstractRepositoryTest {

    private static final int EXPECTED_NUMBER_OF_BOOK = 3;
    private static final int EXPECTED_INSERT_NUMBER_OF_BOOK = 4;
    private static final List<String> BOOKS_TITLE = List.of(
            "Im Westen nichts Neues", "The idiot", "For Whom the Bell Tolls");
    private static final int EXPECTED_NUMBER_OF_GENRE = 2;
    private static final List<String> AUTHORS_FULL_NAME = List.of(
            "Erich Maria Remarque", "Fyodor Dostoyevsky", "Ernest Miller Hemingway");
    private static final String INSERT_TITLE_VALUE = "BookTitle_test_insert";
    private static final String UPDATE_TITLE_VALUE = "BookTitle_test_update";
    private static final int FIRST_BOOK_ID = 0;
    private static final int SECOND_BOOK_ID = 2;

    @Autowired
    private BookService service;

    @Autowired
    private BookRepository repository;

    @Autowired
    private BookConverter converter;

    @DisplayName("должен загружать список всех книг с полной информацией о них")
    @Test
    @Order(1)
    void findAll() {
        var listBookDto = service.findAll();

        assertThat(listBookDto).isNotNull().hasSize(EXPECTED_NUMBER_OF_BOOK)
                .allMatch(b -> !b.getTitle().isEmpty())
                .allMatch(b -> BOOKS_TITLE.contains(b.getTitle()))
                .allMatch(b -> b.getAuthor() != null)
                .allMatch(b -> AUTHORS_FULL_NAME.contains(b.getAuthor().getFullName()))
                .allMatch(b -> b.getGenres().size() == EXPECTED_NUMBER_OF_GENRE);
    }

    @DisplayName("должен загружать информацию о нужном книге по ее id с полной информацией")
    @Test
    @Order(2)
    void findById() {
        var expectedBookDto = converter.toDto(repository.findAll().get(FIRST_BOOK_ID));
        var optionalBookDto = service.findById(expectedBookDto.getId());

        assertThat(optionalBookDto.orElse(null)).usingRecursiveComparison().isEqualTo(expectedBookDto);
    }

    @DisplayName("должен создать книгу с полной информацией")
    @Test
    @Order(3)
    void insert() {
        var firstBook = repository.findAll().get(FIRST_BOOK_ID);
        var authorId = firstBook.getAuthor().getId();
        var listGenreId = firstBook.getGenres().stream().map(Genre::getId).collect(Collectors.toSet());
        var insertBookDto = service.create(INSERT_TITLE_VALUE, authorId, listGenreId);
        var expectedBookDto = converter.toDto(
                Objects.requireNonNull(repository.findById(insertBookDto.getId()).orElse(null)));
        var expectedListBook = repository.findAll();

        assertThat(insertBookDto).usingRecursiveComparison().isEqualTo(expectedBookDto);
        assertThat(expectedBookDto.getTitle()).isEqualTo(INSERT_TITLE_VALUE);
        assertThat(expectedListBook).size().isEqualTo(EXPECTED_INSERT_NUMBER_OF_BOOK);
    }

    @DisplayName("должен обновить книгу с полной информацией")
    @Test
    @Order(4)
    void update() {
        var listBook = repository.findAll();
        var firstBook = listBook.get(FIRST_BOOK_ID);
        var secondBook = listBook.get(SECOND_BOOK_ID);
        var authorId = firstBook.getAuthor().getId();
        var listGenreId = firstBook.getGenres().stream().map(Genre::getId).collect(Collectors.toSet());
        var updateBookDto = service.update(secondBook.getId(), UPDATE_TITLE_VALUE, authorId, listGenreId);
        var expectedBookDto = converter.toDto(
                Objects.requireNonNull(repository.findById(updateBookDto.getId()).orElse(null)));

        assertThat(updateBookDto).usingRecursiveComparison().isEqualTo(expectedBookDto);
    }

    @DisplayName("должен удалять книгу по ее id")
    @Test
    @Order(5)
    void deleteById() {
        var firstBook = repository.findAll().get(FIRST_BOOK_ID);
        service.deleteById(firstBook.getId());
        var optionalExpectedBookDto = service.findById(firstBook.getId());

        assertThat(optionalExpectedBookDto).isEmpty();
    }
}
