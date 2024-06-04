package ru.otus.hw.services;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import ru.otus.hw.AbstractRepositoryTest;
import ru.otus.hw.converters.AuthorConverter;
import ru.otus.hw.converters.BookConverter;
import ru.otus.hw.converters.CommentConverter;
import ru.otus.hw.converters.GenreConverter;
import ru.otus.hw.repositories.BookRepository;
import ru.otus.hw.repositories.CommentRepository;

import java.util.List;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Сервис для работы с комментариями ")
@Import({CommentConverter.class, CommentServiceImpl.class, BookConverter.class,
        AuthorConverter.class, GenreConverter.class, BookServiceImpl.class})
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CommentServiceImplTest extends AbstractRepositoryTest  {

    private static final int FIRST_COMMENT_ID = 0;
    private static final int FIRST_BOOK_ID = 0;
    private static final List<String> COMMENTS_CONTENT= List.of("There is something about" +
            " yourself that you don't know. - Jason Statham", "People take chances every now and then," +
            " and you don't want to disappoint them. - Jason Statham");
    private static final String INSERT_CONTENT_VALUE = "CommentContent_test_insert";
    private static final String UPDATE_CONTENT_VALUE = "CommentContent_test_update";
    private static final int EXPECTED_INSERT_NUMBER_OF_COMMENT = 7;
    private static final int EXPECTED_NUMBER_OF_COMMENT = 2;

    @Autowired
    private CommentService service;

    @Autowired
    private CommentRepository repository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private CommentConverter converter;

    @DisplayName("должен загружать информацию о нужном комментарии по его id с полной информацией")
    @Test
    @Order(1)
    void findById() {
        var expectedCommentDto = converter.toDto(repository.findAll().get(FIRST_COMMENT_ID));
        var optionalCommentDto = service.findById(expectedCommentDto.getId());

        assertThat(optionalCommentDto.orElse(null)).usingRecursiveComparison().isEqualTo(expectedCommentDto);
    }

    @DisplayName("должен загружать информацию о нужных комментариях по id книги с полной информацией")
    @Test
    @Order(2)
    void findAllByBookId() {
        var book = bookRepository.findAll().get(FIRST_BOOK_ID);
        var listCommentDto = service.findAllByBookId(book.getId());

        assertThat(listCommentDto).isNotNull().hasSize(EXPECTED_NUMBER_OF_COMMENT)
                .allMatch(c -> !c.getContent().isEmpty())
                .allMatch(c -> COMMENTS_CONTENT.contains(c.getContent()));
    }

    @DisplayName("должен создать комментарий с полной информацией")
    @Test
    @Order(3)
    void insert() {
        var book = bookRepository.findAll().get(FIRST_BOOK_ID);
        var insertCommentDto = service.create(INSERT_CONTENT_VALUE, book.getId());
        var optionalExpectedCommentDto = service.findById(insertCommentDto.getId());
        var expectedListComment = repository.findAll();

        assertThat(insertCommentDto).usingRecursiveComparison()
                .isEqualTo(optionalExpectedCommentDto.orElse(null));
        assertThat(optionalExpectedCommentDto.orElse(null).getContent()).isEqualTo(INSERT_CONTENT_VALUE);
        assertThat(expectedListComment).size().isEqualTo(EXPECTED_INSERT_NUMBER_OF_COMMENT);
    }

    @DisplayName("должен обновить комментарий с полной информацией")
    @Test
    @Order(4)
    void update() {
        var firstComment = repository.findAll().get(FIRST_COMMENT_ID);
        var updateCommentDto = service.update(firstComment.getId(), UPDATE_CONTENT_VALUE);
        var expectedCommentDto = converter.toDto(
                Objects.requireNonNull(repository.findById(updateCommentDto.getId()).orElse(null)));

        assertThat(updateCommentDto).usingRecursiveComparison().isEqualTo(expectedCommentDto);
        assertThat(expectedCommentDto.getContent()).isEqualTo(UPDATE_CONTENT_VALUE);
    }

    @DisplayName("должен удалять комментарий по его id")
    @Test
    @Order(5)
    void deleteById() {
        var firstComment = repository.findAll().get(FIRST_COMMENT_ID);
        service.deleteById(firstComment.getId());
        var optionalExpectedCommentDto = service.findById(firstComment.getId());

        assertThat(optionalExpectedCommentDto).isEmpty();
    }
}
