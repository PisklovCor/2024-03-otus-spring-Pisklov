package ru.otus.hw.services;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw.mappers.AuthorMapper;
import ru.otus.hw.mappers.BookMapper;
import ru.otus.hw.mappers.CommentMapper;
import ru.otus.hw.mappers.GenreMapper;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hibernate.internal.util.collections.CollectionHelper.setOf;

@DisplayName("Сервис для работы с комментариями ")
@DataJpaTest
@Import({CommentMapper.class, CommentServiceImpl.class, BookMapper.class,
        AuthorMapper.class, GenreMapper.class, BookServiceImpl.class})
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
    private static final Set<String> COMMENT_CONTENT_VALUE = setOf("Content_3", "Content_4");

    @Autowired
    private CommentService service;

    @DisplayName("должен загружать информацию о нужном комментарии по ее id с полной информацией")
    @Test
    @Order(1)
    void findById() {
        var optionalActualCommentDto = service.findById(FIRST_COMMENT_ID);
        assertThat(optionalActualCommentDto).isPresent();
        assertThat(optionalActualCommentDto.get().getId()).isEqualTo(FIRST_COMMENT_ID);
    }

    @DisplayName("должен загружать информацию о нужных комментариях по id книге с полной информацией")
    @Test
    @Order(2)
    void findAllByBookId() {
        var listBookDto = service.findAllByBookId(BOOK_ID);
        assertThat(listBookDto).isNotNull().hasSize(EXPECTED_NUMBER_OF_COMMENT)
                .allMatch(b -> !b.getContent().isEmpty())
                .allMatch(b -> COMMENT_CONTENT_VALUE.contains(b.getContent()));
    }

    @DisplayName("должен создать комментарий с полной информацией")
    @Test
    @Order(3)
    void insert() {
        var insertCommentDto = service.create(INSERT_CONTENT_VALUE, BOOK_ID);
        var optionalExpectedCommentDto = service.findById(insertCommentDto.getId());

        assertThat(insertCommentDto).usingRecursiveComparison().isEqualTo(optionalExpectedCommentDto.orElse(null));

    }

    @DisplayName("должен обновить комментарий с полной информацией")
    @Test
    @Order(4)
    void update() {
        var updateCommentDto = service.update(NEW_COMMENT_ID, UPDATE_CONTENT_VALUE);
        var optionalExpectedCommentDto = service.findById(updateCommentDto.getId());

        assertThat(updateCommentDto).usingRecursiveComparison().isEqualTo(optionalExpectedCommentDto.orElse(null));
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
