package ru.otus.hw.repositories;

import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Comment;

import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hibernate.internal.util.collections.CollectionHelper.setOf;

@DisplayName("Репозиторий на основе Spring Data JPA для работы с комментариями ")
@DataJpaTest
class CommentRepositoryTest {

    private static final int EXPECTED_NUMBER_OF_COMMENT = 2;
    private static final long FIRST_COMMENT_ID = 1L;
    private static final long FIRST_BOOK_ID = 1L;
    private static final long FIRST_BOOK_AUTHOR_ID = 1L;
    private static final String FIRST_COMMENT_CONTENT_VALUE= "Content_1";
    private static final String INSERT_COMMENT_CONTENT_VALUE= "Content_7";
    private static final String UPDATE_COMMENT_CONTENT_VALUE= "Content_10";
    private static final Set<String> COMMENT_CONTENT_FIRST_BOOK_VALUE = setOf("Content_1", "Content_2");

    @Autowired
    private CommentRepository repo;

    @Autowired
    private TestEntityManager em;

    @DisplayName("должен загружать информацию о нужном комментарие по его id с полной информацией")
    @Test
    void findById() {

        val optionalActualComment = repo.findById(FIRST_COMMENT_ID);
        val optionalExpectedComment = Optional.ofNullable(em.find(Comment.class, FIRST_COMMENT_ID));

        assertThat(optionalActualComment).isNotNull();
        assertThat(optionalActualComment).usingRecursiveComparison().isEqualTo(optionalExpectedComment);
        assertThat(optionalActualComment.get().getContent()).isEqualTo(optionalExpectedComment.get().getContent());
        assertThat(optionalActualComment.get().getContent()).contains(FIRST_COMMENT_CONTENT_VALUE);
        assertThat(optionalActualComment.get().getBook().getId()).isEqualTo(optionalExpectedComment.get().getBook().getId());
    }

    @DisplayName("должен загружать информацию о нужном комментариях по id книг с полной информацией")
    @Test
    void findAllByBookId() {
        val actualComment = repo.findAllByBookId(FIRST_BOOK_ID);

        assertThat(actualComment).isNotNull().hasSize(EXPECTED_NUMBER_OF_COMMENT)
                .allMatch(c -> !c.getContent().isEmpty() && COMMENT_CONTENT_FIRST_BOOK_VALUE.contains(c.getContent()))
                .allMatch(c -> c.getBook().getId().equals(FIRST_BOOK_ID))
                .allMatch(c -> c.getBook().getAuthor() != null
                        && !c.getBook().getAuthor().getFullName().isEmpty()
                && c.getBook().getAuthor().getId() == FIRST_BOOK_AUTHOR_ID)
                .allMatch(c -> c.getBook().getGenres() != null
                        && c.getBook().getGenres().size() > 1)
                .allMatch(c -> !c.getBook().getGenres().get(1).getName().isEmpty());
    }

    @DisplayName("должен создать комментарий с полной информацией")
    @Test
    void save_insert() {

        Comment comment = new Comment();
        comment.setContent(INSERT_COMMENT_CONTENT_VALUE);

        val expectedBook = em.find(Book.class, FIRST_BOOK_ID);

        comment.setBook(expectedBook);

        val insertComment = repo.save(comment);
        val expectedComment = em.find(Comment.class, insertComment.getId());

        assertThat(insertComment).isNotNull()
                .isEqualTo(expectedComment);
        assertThat(insertComment.getContent())
                .isEqualTo(INSERT_COMMENT_CONTENT_VALUE);
        assertThat(insertComment.getBook())
                .isNotNull();
        assertThat(insertComment.getBook()).isEqualTo(expectedBook);
        assertThat(insertComment.getBook().getGenres()).hasSize(2)
                .allMatch(g -> !g.getName().isEmpty());

    }

    @DisplayName("должен обнвоить комментарий с полной информацией")
    @Test
    void save_update() {

        Comment comment = new Comment();
        comment.setId(FIRST_COMMENT_ID);
        comment.setContent(UPDATE_COMMENT_CONTENT_VALUE);
        comment.setBook(em.find(Book.class, FIRST_BOOK_ID));

        val originalCommentContent = em.find(Comment.class, comment.getId()).getContent();
        val updateComment = repo.save(comment);
        val expectedComment = em.find(Comment.class, updateComment.getId());

        assertThat(updateComment.getId()).isEqualTo(FIRST_COMMENT_ID);
        assertThat(updateComment.getContent()).isNotEqualTo(originalCommentContent);
        assertThat(updateComment.getContent()).isEqualTo(UPDATE_COMMENT_CONTENT_VALUE);
        assertThat(updateComment).isNotNull()
                .isEqualTo(expectedComment);
        assertThat(updateComment.getBook().getId()).isEqualTo(FIRST_BOOK_ID);
        assertThat(updateComment.getBook().getAuthor())
                .isNotNull();
        assertThat(updateComment.getBook().getGenres()).hasSize(2)
                .allMatch(g -> !g.getName().isEmpty());
    }

    @DisplayName("должен удалять комментарий по ее id")
    @Test
    void deleteById() {
        repo.deleteById(FIRST_COMMENT_ID);
        val expectedBook = em.find(Comment.class, FIRST_COMMENT_ID);

        assertThat(expectedBook).isNull();
    }
}