package ru.otus.hw.clients;

import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import ru.otus.hw.dto.library.AuthorDto;
import ru.otus.hw.dto.library.BookCreateDto;
import ru.otus.hw.dto.library.BookDto;
import ru.otus.hw.dto.library.GenreDto;
import ru.otus.hw.exceptions.ExternalSystemException;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static ru.otus.hw.dictionaries.ExternalSystem.LIBRARY_SERVICE;

@Slf4j
@Service
@RequiredArgsConstructor
public class LibraryClient {

    private static final String BOOK_CREATE = "library-service/api/v1/book";

    private static final String AUTHOR_LIST = "library-service/api/v1/author";

    private static final String GENRE_LIST = "library-service/api/v1/genre";

    private final RestClient.Builder libraryRestClientBuilder;

    public BookDto createBook(BookCreateDto dto) {
        return libraryRestClientBuilder.build().post()
                .uri(BOOK_CREATE)
                .contentType(APPLICATION_JSON)
                .body(dto)
                .retrieve()
                .onStatus(status -> status.value() == 500, (request, response) -> {
                    throw new ExternalSystemException("Error creating book", LIBRARY_SERVICE);
                })
                .body(BookDto.class);
    }

    @Retry(name = "retryCacheScheduled", fallbackMethod = "recoverMethod")
    public List<AuthorDto> getListAuthor() {
        return libraryRestClientBuilder.build().get()
                .uri(AUTHOR_LIST)
                .accept(APPLICATION_JSON)
                .retrieve()
                .onStatus(status -> status.value() == 500, (request, response) -> {
                    throw new ExternalSystemException("Error get Authors", LIBRARY_SERVICE);
                })
                .body(new ParameterizedTypeReference<>() {});
    }

    @Retry(name = "retryCacheScheduled", fallbackMethod = "recoverMethod")
    public List<GenreDto> getListGenre() {
        return libraryRestClientBuilder.build().get()
                .uri(GENRE_LIST)
                .accept(APPLICATION_JSON)
                .retrieve()
                .onStatus(status -> status.value() == 500, (request, response) -> {
                    throw new ExternalSystemException("Error get Genres", LIBRARY_SERVICE);
                })
                .body(new ParameterizedTypeReference<>() {});
    }

    private List<Objects> recoverMethod(Throwable throwable) {
        log.error("Worked CircuitBreaker, e=[{}]", throwable.getMessage());
        return Collections.emptyList();
    }
}
