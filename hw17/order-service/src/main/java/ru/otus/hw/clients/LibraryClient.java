package ru.otus.hw.clients;

import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import ru.otus.hw.dto.AuthorDto;
import ru.otus.hw.dto.BookCreateDto;
import ru.otus.hw.dto.BookDto;
import ru.otus.hw.dto.GenreDto;
import ru.otus.hw.exceptions.ExternalSystemException;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static ru.otus.hw.dictionaries.ExternalSystem.LIBRARY_SERVICE;

@RequiredArgsConstructor
@Service
public class LibraryClient {

    private static final String BOOK_CREATE = "/api/v1/book";

    private static final String AUTHOR_LIST = "/api/v1/author";

    private static final String GENRE_LIST = "/api/v1/genre";

    private final RestClient libraryRestClient;

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
}
