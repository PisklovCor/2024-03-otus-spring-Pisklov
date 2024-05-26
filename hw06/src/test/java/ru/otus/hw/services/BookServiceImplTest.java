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
import ru.otus.hw.repositories.*;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hibernate.internal.util.collections.CollectionHelper.setOf;

@DisplayName("Сервис для работы с книгами ")
@DataJpaTest
@Import({BookConverter.class, AuthorConverter.class, GenreConverter.class,
        JpaBookRepository.class, JpaAuthorRepository.class,
        JpaGenreRepository.class, BookServiceImpl.class})
@Transactional(propagation = Propagation.NEVER)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class BookServiceImplTest {

    private static final int EXPECTED_NUMBER_OF_BOOK = 3;
    private static final long FIRST_BOOK_ID = 2L;
    private static final long NEW_BOOK_ID = 4L;
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
        assertThat(optionalActualBookDto.get().getAuthor().getFullName()).isEqualTo("Author_2");
        assertThat(optionalActualBookDto.get().getGenres()).isNotNull()
                .allMatch(g -> !g.getName().isEmpty());
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

    @DisplayName("должен обновить книгу с полной информацией")
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
