package ru.otus.hw.repositories;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Genre;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий на основе Jpa для работы с книгами ")
@DataJpaTest
@Import(BookRepositoryJpa.class)
class BookRepositoryJpaTest {

    private static final int EXPECTED_NUMBER_OF_BOOK = 3;
    private static final long FIRST_BOOK_ID = 2L;

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private BookRepository repositoryJpa;

    @DisplayName("должен загружать список всех книг с полной информацией о них")
    @Test
    void findAll() {

        val actualBook = repositoryJpa.findAll();

        assertThat(actualBook).isNotNull().hasSize(EXPECTED_NUMBER_OF_BOOK)
                .allMatch(b -> !b.getTitle().isEmpty())
                .allMatch(b -> b.getAuthor() != null && !b.getAuthor().getFullName().isEmpty())
                .allMatch(b -> b.getGenres() != null && b.getGenres().size() > 1)
                .allMatch(b -> !b.getGenres().get(1).getName().isEmpty());
    }

    @DisplayName("должен загружать информацию о нужном книге по ее id с полной информацией")
    @Test
    void findById() {

        val optionalActualBook = repositoryJpa.findById(FIRST_BOOK_ID);
        val expectedBook = em.find(Book.class, FIRST_BOOK_ID);

        assertThat(optionalActualBook).isPresent().get()
                .usingRecursiveComparison().isEqualTo(expectedBook);
    }

    @DisplayName("должен создать книгу с полной информацией")
    @Test
    void save_insert() {

        Book book = new Book();
        book.setTitle("BookTitle_4");
        book.setAuthor(em.find(Author.class, 1));
        book.setGenres(Collections.singletonList(em.find(Genre.class, 3)));

        val insertBook = repositoryJpa.save(book);
        val expectedBook = em.find(Book.class, insertBook.getId());

        assertThat(insertBook).isNotNull()
                .isEqualTo(expectedBook);
        assertThat(insertBook.getAuthor())
                .isNotNull();
        assertThat(insertBook.getGenres()).hasSize(1)
                .allMatch(g -> !g.getName().isEmpty());
    }

    @DisplayName("должен обнвоить книгу с полной информацией")
    @Test
    void save_update() {

        Book book = new Book();
        book.setId(2);
        book.setTitle("BookTitle_5");
        book.setAuthor(em.find(Author.class, 1));
        book.setGenres(Collections.singletonList(em.find(Genre.class, 3)));

        val originalBookTitle = em.find(Book.class, book.getId()).getTitle();
        val updateBook = repositoryJpa.save(book);
        val expectedBook = em.find(Book.class, updateBook.getId());

        assertThat(updateBook.getId()).isEqualTo(2);
        assertThat(updateBook.getTitle()).isNotEqualTo(originalBookTitle);
        assertThat(updateBook).isNotNull()
                .isEqualTo(expectedBook);
        assertThat(updateBook.getAuthor())
                .isNotNull();
        assertThat(updateBook.getGenres()).hasSize(1)
                .allMatch(g -> !g.getName().isEmpty());
    }

    @DisplayName("должен удалять книгу по ее id")
    @Test
    void deleteById() {

        repositoryJpa.deleteById(FIRST_BOOK_ID);
        val expectedBook = em.find(Book.class, FIRST_BOOK_ID);

        assertThat(expectedBook).isNull();
    }
}
