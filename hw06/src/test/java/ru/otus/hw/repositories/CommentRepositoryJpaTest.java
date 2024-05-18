package ru.otus.hw.repositories;

import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Comment;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Репозиторий на основе Jpa для работы с комментариями ")
@DataJpaTest
@Import(CommentRepositoryJpa.class)
class CommentRepositoryJpaTest {

    private static final int EXPECTED_NUMBER_OF_COMMENT = 2;
    private static final long FIRST_COMMENT_ID = 1L;
    private static final long FIRST_BOOK_ID = 2L;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private TestEntityManager em;

    @DisplayName("должен загружать информацию о нужном комментарие по его id с полной информацией")
    @Test
    void findById() {

        val optionalActualComment = commentRepository.findById(FIRST_COMMENT_ID);
        val expectedComment = em.find(Comment.class, FIRST_COMMENT_ID);

        assertThat(optionalActualComment).isNotNull();
        assertThat(optionalActualComment).get().usingRecursiveComparison().isEqualTo(expectedComment);
        assertThat(optionalActualComment.get().getContent()).isEqualTo(expectedComment.getContent());
        assertThat(optionalActualComment.get().getBook().getId()).isEqualTo(expectedComment.getBook().getId());
    }

    @DisplayName("должен загружать информацию о нужном комментариях по id книг с полной информацией")
    @Test
    void findAllByBookId() {
        val actualComment = commentRepository.findAllByBookId(FIRST_BOOK_ID);

        assertThat(actualComment).isNotNull().hasSize(EXPECTED_NUMBER_OF_COMMENT)
                .allMatch(c -> !c.getContent().isEmpty())
                .allMatch(c -> c.getBook().getAuthor() != null
                        && !c.getBook().getAuthor().getFullName().isEmpty())
                .allMatch(c -> c.getBook().getGenres() != null
                        && c.getBook().getGenres().size() > 1)
                .allMatch(c -> !c.getBook().getGenres().get(1).getName().isEmpty());
    }

    @Test
    void save_insert() {
    }

    @Test
    void save_update() {
    }

    @DisplayName("должен удалять комментарий по ее id")
    @Test
    void deleteById() {
        commentRepository.deleteById(FIRST_COMMENT_ID);
        val expectedBook = em.find(Comment.class, FIRST_COMMENT_ID);

        assertThat(expectedBook).isNull();
    }
}