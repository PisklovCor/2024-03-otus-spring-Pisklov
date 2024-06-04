package ru.otus.hw.events;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import ru.otus.hw.AbstractRepositoryTest;
import ru.otus.hw.repositories.BookRepository;
import ru.otus.hw.repositories.CommentRepository;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Сервис для каскадного удаление клмментарие по id книги ")
@Import(MongoBookCascadeDeleteEventsListener.class)
class MongoBookCascadeDeleteEventsListenerTest extends AbstractRepositoryTest {

    private static final int FIRST_BOOK_ID = 0;
    private static final int EXPECTED_DELETE_NUMBER_OF_BOOK = 2;
    private static final int EXPECTED_DELETE_NUMBER_OF_COMMENT = 4;

    @Autowired
    private BookRepository repository;

    @Autowired
    private CommentRepository commentRepository;

    @DisplayName("должен удалить все коментарии по удаленной книге")
    @Test
    void onAfterDelete() {

        var firstBook = repository.findAll().get(FIRST_BOOK_ID);
        repository.deleteById(firstBook.getId());
        var optionalExpectedBookDto = repository.findById(firstBook.getId());
        var listBook = repository.findAll();
        var listComment = commentRepository.findAll();

        assertThat(optionalExpectedBookDto).isEmpty();
        assertThat(listBook.size()).isEqualTo(EXPECTED_DELETE_NUMBER_OF_BOOK);
        assertThat(listComment.size()).isEqualTo(EXPECTED_DELETE_NUMBER_OF_COMMENT);
    }
}