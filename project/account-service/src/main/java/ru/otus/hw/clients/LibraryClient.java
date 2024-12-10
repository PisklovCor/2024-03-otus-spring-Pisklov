package ru.otus.hw.clients;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import ru.otus.hw.dto.library.BookDto;
import ru.otus.hw.dto.library.CommentDto;
import ru.otus.hw.exceptions.ExternalSystemException;

import java.util.Collections;
import java.util.List;

import static ru.otus.hw.dictionaries.ExternalSystem.LIBRARY_SERVICE;

@Slf4j
@Service
@RequiredArgsConstructor
public class LibraryClient {

    private static final String BOOK_BY_ID = "library-service/api/v1/book/{bookId}";

    private static final String COMMENT_BY_LOGIN = "library-service/api/v1/comment?login={login}";

    private final RestClient.Builder libraryRestClientBuilder;

    @CircuitBreaker(name = "circuitBreakerLibraryBookRestClient", fallbackMethod = "recoverBookMethod")
    public BookDto getBookById(long bookId) {
        return libraryRestClientBuilder.build().get()
                .uri(BOOK_BY_ID , bookId)
                .retrieve()
                .onStatus(status -> status.value() == 500, (request, response) -> {
                    throw new ExternalSystemException("Error get book", LIBRARY_SERVICE);
                })
                .body(BookDto.class);
    }

    private BookDto recoverBookMethod(long bookId, Throwable throwable) {
        log.error("Worked circuitBreakerLibraryBookRestClient, bookId: {} throwable: {}",
                bookId, throwable.getMessage());
        var bookDto = new BookDto();
        bookDto.setId(-1);
        return bookDto;
    }

    @CircuitBreaker(name = "circuitBreakerLibraryCommentRestClient", fallbackMethod = "recoverCommentMethod")
    public List<CommentDto> getCommentByUserLogin(String login) {
        return libraryRestClientBuilder.build().get()
                .uri(COMMENT_BY_LOGIN, login)
                .retrieve()
                .onStatus(status -> status.value() == 500, (request, response) -> {
                    throw new ExternalSystemException("Error get comment", LIBRARY_SERVICE);
                })
                .body(new ParameterizedTypeReference<>() {
                });
    }

    private List<CommentDto> recoverCommentMethod(String login, Throwable throwable) {
        log.error("Worked circuitBreakerLibraryCommentRestClient, login: {} throwable: {}",
                login, throwable.getMessage());
        return Collections.emptyList();
    }
}
