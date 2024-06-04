package ru.otus.hw.repositories;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.otus.hw.AbstractRepositoryTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
@DisplayName("Репозиторий на основе MongoDb для работы с комментариями ")
class CommentRepositoryTest extends AbstractRepositoryTest {

    private static final int FIRST_BOOK_ID = 0;
    private static final int EXPECTED_NUMBER_OF_COMMENT = 2;
    private static final List<String> BOOKS_TITLE = List.of("Im Westen nichts Neues");
    private static final List<String> AUTHORS_FULL_NAME = List.of("Erich Maria Remarque");

    @Autowired
    private CommentRepository repository;

    @Autowired
    private BookRepository bookRepository;

    @DisplayName("должен загружать информацию о комментариях по id книг с полной информацией")
    @Test
    void findAllByBookId() {
       var book = bookRepository.findAll().get(FIRST_BOOK_ID);
       var listCommentByBookId = repository.findAllByBookId(book.getId());

        assertThat(listCommentByBookId).isNotNull().hasSize(EXPECTED_NUMBER_OF_COMMENT)
                .allMatch(c -> !c.getContent().isEmpty())
                .allMatch(c -> c.getBook() != null)
                .allMatch(c -> BOOKS_TITLE.contains(c.getBook().getTitle()))
                .allMatch(c -> c.getBook().getAuthor() != null)
                .allMatch(c -> AUTHORS_FULL_NAME.contains(c.getBook().getAuthor().getName()));
    }
}