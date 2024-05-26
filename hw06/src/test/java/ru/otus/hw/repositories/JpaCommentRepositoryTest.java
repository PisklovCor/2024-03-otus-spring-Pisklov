package ru.otus.hw.repositories;

import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Comment;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий на основе Jpa для работы с комментариями ")
@DataJpaTest
@Import(JpaCommentRepository.class)
class JpaCommentRepositoryTest {

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

    @DisplayName("должен создать комментарий с полной информацией")
    @Test
    void save_insert() {

        Comment comment = new Comment();
        comment.setContent("content_7");
        comment.setBook(em.find(Book.class, FIRST_BOOK_ID));

        val insertComment = commentRepository.save(comment);
        val expectedComment = em.find(Comment.class, insertComment.getId());

        assertThat(insertComment).isNotNull()
                .isEqualTo(expectedComment);
        assertThat(insertComment.getBook())
                .isNotNull();
        assertThat(insertComment.getBook().getGenres()).hasSize(2)
                .allMatch(g -> !g.getName().isEmpty());

    }

    @DisplayName("должен обнвоить комментарий с полной информацией")
    @Test
    void save_update() {

        Comment comment = new Comment();
        comment.setId(FIRST_COMMENT_ID);
        comment.setContent("content_7");
        comment.setBook(em.find(Book.class, FIRST_BOOK_ID));

        val originalCommentContent = em.find(Comment.class, comment.getId()).getContent();
        val updateComment = commentRepository.save(comment);
        val expectedComment = em.find(Comment.class, updateComment.getId());

        assertThat(updateComment.getId()).isEqualTo(FIRST_COMMENT_ID);
        assertThat(updateComment.getContent()).isNotEqualTo(originalCommentContent);
        assertThat(updateComment).isNotNull()
                .isEqualTo(expectedComment);
        assertThat(updateComment.getBook().getAuthor())
                .isNotNull();
        assertThat(updateComment.getBook().getGenres()).hasSize(2)
                .allMatch(g -> !g.getName().isEmpty());
    }

    @DisplayName("должен удалять комментарий по ее id")
    @Test
    void deleteById() {
        commentRepository.deleteById(FIRST_COMMENT_ID);
        val expectedBook = em.find(Comment.class, FIRST_COMMENT_ID);

        assertThat(expectedBook).isNull();
    }
}