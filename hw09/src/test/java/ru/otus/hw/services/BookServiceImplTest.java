package ru.otus.hw.services;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw.converters.AuthorConverter;
import ru.otus.hw.converters.BookConverter;
import ru.otus.hw.converters.GenreConverter;
import ru.otus.hw.dto.AuthorDto;
import ru.otus.hw.dto.BookDto;
import ru.otus.hw.dto.GenreDto;

import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hibernate.internal.util.collections.CollectionHelper.setOf;

@DisplayName("Сервис для работы с книгами ")
@DataJpaTest
@Import({BookConverter.class, AuthorConverter.class, GenreConverter.class, BookServiceImpl.class})
@Transactional(propagation = Propagation.NEVER)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class BookServiceImplTest {

    private static final int EXPECTED_NUMBER_OF_BOOK = 3;
    private static final long FIRST_BOOK_ID = 1L;
    private static final long FIRST_AUTHOR_ID = 1L;
    private static final long UPDATE_AUTHOR_ID = 2L;
    private static final long UPDATE_BOOK_ID = 2L;
    private static final long NEW_BOOK_ID = 4L;
    private static final long GENRE_ID = 3L;
    private static final String INSERT_TITLE_VALUE = "BookTitle_4";
    private static final String UPDATE_TITLE_VALUE = "BookTitle_5";
    private static final Set<String> AUTHOR_FULL_NAME = setOf("Author_1", "Author_2", "Author_3");

    @Autowired
    private BookService service;

    @DisplayName("должен загружать список всех книг с полной информацией о них")
    @Test
    @Order(1)
    void findAll() {
        var listBookDto = service.findAll();
        assertThat(listBookDto).isNotNull().hasSize(EXPECTED_NUMBER_OF_BOOK)
                .allMatch(b -> !b.getTitle().isEmpty())
                .allMatch(b -> b.getAuthor() != null)
                .allMatch(b -> AUTHOR_FULL_NAME.contains(b.getAuthor().getFullName()))
                .allMatch(b -> b.getGenres().size() == 2);
    }

    @DisplayName("должен загружать информацию о нужном книге по ее id с полной информацией")
    @Test
    @Order(2)
    void findById() {
        var optionalActualBookDto = service.findById(FIRST_BOOK_ID);
        assertThat(optionalActualBookDto).isPresent();
        assertThat(optionalActualBookDto.get().getId()).isEqualTo(FIRST_BOOK_ID);
        assertThat(optionalActualBookDto.get().getAuthor()).isNotNull();
        assertThat(optionalActualBookDto.get().getAuthor().getId()).isEqualTo(FIRST_AUTHOR_ID);
        assertThat(optionalActualBookDto.get().getGenres()).isNotNull()
                .allMatch(g -> !g.getName().isEmpty());
        assertThat(optionalActualBookDto.get().getGenres().size()).isEqualTo(2);
    }

    @DisplayName("должен создать книгу с полной информацией")
    @Test
    @Order(3)
    void insert() {
        var insertBookDto = service.create(bildBookDto(0, INSERT_TITLE_VALUE, FIRST_AUTHOR_ID));
        var optionalExpectedBookDto = service.findById(insertBookDto.getId());

        assertThat(insertBookDto).usingRecursiveComparison().isEqualTo(optionalExpectedBookDto.orElse(null));
    }

    @DisplayName("должен обновить книгу с полной информацией")
    @Test
    @Order(4)
    void update() {
        var updateBookDto = service.update(bildBookDto(UPDATE_BOOK_ID, UPDATE_TITLE_VALUE, UPDATE_AUTHOR_ID));
        var optionalExpectedBookDto = service.findById(updateBookDto.getId());

        assertThat(updateBookDto).usingRecursiveComparison().isEqualTo(optionalExpectedBookDto.orElse(null));
    }

    @DisplayName("должен удалять книгу по ее id (созданну в тесте)")
    @Test
    @Order(5)
    void deleteById() {
        service.deleteById(NEW_BOOK_ID);
        var optionalActualBookDto = service.findById(NEW_BOOK_ID);
        assertThat(optionalActualBookDto).isEmpty();
    }

    private BookDto bildBookDto(long bookId, String title, long authorId) {
        AuthorDto authorDto = new AuthorDto();
        authorDto.setId(authorId);

        GenreDto genreDto = new GenreDto();
        genreDto.setId(GENRE_ID);

        BookDto bookDto = new BookDto();
        bookDto.setId(bookId);
        bookDto.setTitle(title);
        bookDto.setAuthor(authorDto);
        bookDto.setGenres(List.of(genreDto));

        return bookDto;
    }
}
