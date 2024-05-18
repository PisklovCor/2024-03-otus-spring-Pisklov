package ru.otus.hw.services;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw.converters.AuthorConverter;
import ru.otus.hw.converters.BookConverter;
import ru.otus.hw.converters.GenreConverter;
import ru.otus.hw.repositories.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hibernate.internal.util.collections.CollectionHelper.setOf;

@DisplayName("Сервис для работы с книгами ")
@DataJpaTest
@Import({BookConverter.class, AuthorConverter.class, GenreConverter.class,
        BookRepositoryJpa.class, AuthorRepositoryJpa.class,
        GenreRepositoryJpa.class, BookServiceImpl.class})
@Transactional(propagation = Propagation.NEVER)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class BookServiceImplTest {

    private static final int EXPECTED_NUMBER_OF_BOOK = 3;
    private static final long FIRST_BOOK_ID = 2L;
    private static final long NEW_BOOK_ID = 4L;

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
                .allMatch(b -> !b.getGenres().isEmpty() && b.getGenres().size() > 1);
    }

    @DisplayName("должен загружать информацию о нужном книге по ее id с полной информацией")
    @Test
    @Order(2)
    void findById() {
        var optionalActualBookDto = service.findById(FIRST_BOOK_ID);
        assertThat(optionalActualBookDto).isPresent();
        assertThat(optionalActualBookDto.get().getId()).isEqualTo(FIRST_BOOK_ID);
        assertThat(optionalActualBookDto.get().getAuthor()).isNotNull();
        assertThat(optionalActualBookDto.get().getGenres()).isNotNull();
        assertThat(optionalActualBookDto.get().getGenres().size()).isEqualTo(2);
    }

    @DisplayName("должен создать книгу с полной информацией")
    @Test
    @Order(3)
    void insert() {
        var insertBookDto = service.insert("BookTitle_4", 1, setOf(3L));
        var optionalExpectedBookDto = service.findById(insertBookDto.getId());

        assertThat(insertBookDto).isEqualTo(optionalExpectedBookDto.get());
        assertThat(insertBookDto).isNotNull();
        assertThat(insertBookDto.getAuthor())
                .isNotNull();
        assertThat(insertBookDto.getGenres()).hasSize(1)
                .allMatch(g -> !g.getName().isEmpty());
    }

    @DisplayName("должен обнвоить книгу с полной информацией")
    @Test
    @Order(4)
    void update() {
        var insertBookDto = service.update(2, "BookTitle_5", 1, setOf(3L));
        var optionalExpectedBookDto = service.findById(insertBookDto.getId());

        assertThat(insertBookDto).isEqualTo(optionalExpectedBookDto.get());
        assertThat(insertBookDto).isNotNull();
        assertThat(insertBookDto.getAuthor())
                .isNotNull();
        assertThat(insertBookDto.getGenres()).hasSize(1)
                .allMatch(g -> !g.getName().isEmpty());
    }

    @DisplayName("должен удалять книгу по ее id (созданну в тесте)")
    @Test
    @Order(5)
    void deleteById() {
        service.deleteById(NEW_BOOK_ID);
        var optionalActualBookDto = service.findById(NEW_BOOK_ID);
        assertThat(optionalActualBookDto).isEmpty();
    }
}
