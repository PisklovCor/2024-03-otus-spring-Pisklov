package ru.otus.hw.clients;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import ru.otus.hw.dto.AuthorDto;
import ru.otus.hw.dto.BookCreateDto;
import ru.otus.hw.dto.BookDto;
import ru.otus.hw.dto.GenreDto;
import ru.otus.hw.exceptions.ExternalSystemException;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static ru.otus.hw.dictionaries.ExternalSystem.LIBRARY_SERVICE;

@Slf4j
@RequiredArgsConstructor
@Service
public class LibraryClient {

    private static final String BOOK_CREATE = "/api/v1/book";

    private static final String AUTHOR_LIST = "/api/v1/author";

    private static final String GENRE_LIST = "/api/v1/genre";

    private final RestClient libraryRestClient;

    @RateLimiter(name = "rateLimiter")
    public BookDto createBook(BookCreateDto dto) {
        return libraryRestClient.post()
                .uri(BOOK_CREATE)
                .contentType(APPLICATION_JSON)
                .body(dto)
                .retrieve()
                .onStatus(status -> status.value() == 500, (request, response) -> {
                    throw new ExternalSystemException("Error creating book", LIBRARY_SERVICE);
                })
                .body(BookDto.class);
    }

    @Retry(name = "retryAuthor", fallbackMethod = "recoverMethod")
    public List<AuthorDto> getListAuthor() {
        return libraryRestClient.get()
                .uri(AUTHOR_LIST)
                .accept(APPLICATION_JSON)
                .retrieve()
                .onStatus(status -> status.value() == 500, (request, response) -> {
                    throw new ExternalSystemException("Error get Authors", LIBRARY_SERVICE);
                })
                .body(new ParameterizedTypeReference<>() {});
    }

    @CircuitBreaker(name = "circuitBreakerGenre", fallbackMethod = "recoverMethod")
    public List<GenreDto> getListGenre() {
        return libraryRestClient.get()
                .uri(GENRE_LIST)
                .accept(APPLICATION_JSON)
                .retrieve()
                .onStatus(status -> status.value() == 500, (request, response) -> {
                    throw new ExternalSystemException("Error get Genres", LIBRARY_SERVICE);
                })
                .body(new ParameterizedTypeReference<>() {});
    }

    private List<Objects> recoverMethod(Exception ex) {
        log.warn("Worked CircuitBreaker, e=[{}]", ex.getMessage());
        return Collections.emptyList();
    }
}
