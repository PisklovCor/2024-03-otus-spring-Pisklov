package ru.otus.hw.clients;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import ru.otus.hw.dto.BookDto;
import ru.otus.hw.exceptions.ExternalSystemException;

import static ru.otus.hw.dictionaries.ExternalSystem.LIBRARY_SERVICE;

@Slf4j
@RequiredArgsConstructor
@Service
public class LibraryClient {

    private static final String BOOK_BY_ID = "/api/v1/book/";


    private final RestClient libraryRestClient;

    public BookDto getBookById(long bookId) {
        return libraryRestClient.get()
                .uri(BOOK_BY_ID + bookId)
                .retrieve()
                .onStatus(status -> status.value() == 500, (request, response) -> {
                    throw new ExternalSystemException("Error creating book", LIBRARY_SERVICE);
                })
                .body(BookDto.class);
    }
}
