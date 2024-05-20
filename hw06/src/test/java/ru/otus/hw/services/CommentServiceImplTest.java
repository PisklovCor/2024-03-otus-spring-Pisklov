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
import ru.otus.hw.converters.CommentConverter;
import ru.otus.hw.converters.GenreConverter;
import ru.otus.hw.repositories.AuthorRepositoryJpa;
import ru.otus.hw.repositories.BookRepositoryJpa;
import ru.otus.hw.repositories.CommentRepositoryJpa;
import ru.otus.hw.repositories.GenreRepositoryJpa;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hibernate.internal.util.collections.CollectionHelper.setOf;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Сервис для работы с комментариями ")
@DataJpaTest
@Import({CommentConverter.class, CommentServiceImpl.class,
        CommentRepositoryJpa.class, BookConverter.class,
        AuthorConverter.class, GenreConverter.class,
        BookRepositoryJpa.class, AuthorRepositoryJpa.class,
        GenreRepositoryJpa.class, BookServiceImpl.class})
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
@Transactional(propagation = Propagation.NEVER)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CommentServiceImplTest {

    private static final int EXPECTED_NUMBER_OF_COMMENT = 2;
    private static final long NEW_COMMENT_ID = 7L;
    private static final long FIRST_COMMENT_ID = 2L;
    private static final long BOOK_ID = 2L;
    private static final String INSERT_CONTENT_VALUE = "Content_7";
    private static final String UPDATE_CONTENT_VALUE = "Content_8";

    @Autowired
    private CommentService service;

    @DisplayName("должен загружать информацию о нужном комментарии по ее id с полной информацией")
    @Test
    @Order(1)
    void findById() {
        var optionalActualCommentDto = service.findById(FIRST_COMMENT_ID);
        assertThat(optionalActualCommentDto).isPresent();
        assertThat(optionalActualCommentDto.get().getId()).isEqualTo(FIRST_COMMENT_ID);
        assertThat(optionalActualCommentDto.get().getBook()).isNotNull();
        assertThat(optionalActualCommentDto.get().getBook().getAuthor()).isNotNull();
        assertThat(optionalActualCommentDto.get().getBook().getGenres()).isNotNull();
        assertThat(optionalActualCommentDto.get().getBook().getGenres().size()).isEqualTo(2);
    }

    @DisplayName("должен загружать информацию о нужных комментариях по id книге с полной информацией")
    @Test
    @Order(2)
    void findAllByBookId() {
        var listBookDto = service.findAllByBookId(BOOK_ID);
        assertThat(listBookDto).isNotNull().hasSize(EXPECTED_NUMBER_OF_COMMENT)
                .allMatch(b -> !b.getContent().isEmpty())
                .allMatch(b -> b.getBook() != null)
                .allMatch(b -> !b.getBook().getGenres().isEmpty()
                        && b.getBook().getGenres().size() > 1);
    }

    @DisplayName("должен создать комментарий с полной информацией")
    @Test
    @Order(3)
    void insert() {
        var insertCommentDto = service.insert(INSERT_CONTENT_VALUE, BOOK_ID);
        var optionalExpectedCommentDto = service.findById(insertCommentDto.getId());

        assertThat(insertCommentDto).isEqualTo(optionalExpectedCommentDto.get());
        assertThat(insertCommentDto).isNotNull();
        assertThat(insertCommentDto.getContent()).isEqualTo(INSERT_CONTENT_VALUE);
        assertThat(insertCommentDto.getBook())
                .isNotNull();
        assertThat(insertCommentDto.getBook().getGenres()).hasSize(2)
                .allMatch(g -> !g.getName().isEmpty());

    }

    @DisplayName("должен обновить комментарий с полной информацией")
    @Test
    @Order(4)
    void update() {
        var insertCommentDto = service.update(NEW_COMMENT_ID,UPDATE_CONTENT_VALUE, BOOK_ID);
        var optionalExpectedCommentDto = service.findById(insertCommentDto.getId());

        assertThat(insertCommentDto).isEqualTo(optionalExpectedCommentDto.get());
        assertThat(insertCommentDto).isNotNull();
        assertThat(insertCommentDto.getContent()).isEqualTo(UPDATE_CONTENT_VALUE);
        assertThat(insertCommentDto.getBook())
                .isNotNull();
        assertThat(insertCommentDto.getBook().getGenres()).hasSize(2)
                .allMatch(g -> !g.getName().isEmpty());
    }

    @DisplayName("должен удалять комментарий по ее id (созданный в тесте)")
    @Test
    @Order(5)
    void deleteById() {
        service.deleteById(NEW_COMMENT_ID);
        var optionalActualBookDto = service.findById(NEW_COMMENT_ID);
        assertThat(optionalActualBookDto).isEmpty();
    }
}
