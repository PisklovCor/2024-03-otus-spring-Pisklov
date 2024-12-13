package ru.otus.hw.handlers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import ru.otus.hw.dto.BookCreateDto;
import ru.otus.hw.dto.BookDto;
import ru.otus.hw.dto.BookUpdateDto;
import ru.otus.hw.mappers.BookMapper;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Genre;
import ru.otus.hw.repositories.BookRepository;
import ru.otus.hw.services.DataAcquisitionService;

import java.net.URI;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.ServerResponse.accepted;
import static org.springframework.web.reactive.function.server.ServerResponse.created;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookHandler {

    private final BookRepository repository;

    private final BookMapper mapper;

    private final DataAcquisitionService data;

    public Mono<ServerResponse> createBook(ServerRequest request) {

        Mono<BookCreateDto> dtoMono = request.bodyToMono(BookCreateDto.class);
        Mono<Author> authorMono = dtoMono.flatMap(dto -> data.findMonoAuthorById(dto.getAuthorId()));
        Mono<List<Genre>> genres = dtoMono.flatMap(dto -> data.findAllMonoGenreByIds(dto.getGenresId()));

        Mono<BookDto> result = Mono.zip(dtoMono, authorMono, genres)
                .map(t -> mapper.toEntity(t.getT1(), t.getT2(), t.getT3()))
                .flatMap(repository::save)
                .map(mapper::toDto);

        var bookId = result.map(BookDto::getId);
        log.info("New book has been created, id; {}", bookId);

        return created(URI.create("/route/v1/book/%s".formatted(bookId)))
                .contentType(APPLICATION_JSON).body(result, BookDto.class);
    }

    public Mono<ServerResponse> updateBook(ServerRequest request) {

        Mono<BookUpdateDto> dtoMono = request.bodyToMono(BookUpdateDto.class);
        Mono<Author> authorMono = dtoMono.flatMap(dto -> data.findMonoAuthorById(dto.getAuthorId()));
        Mono<List<Genre>> genres = dtoMono.flatMap(dto -> data.findAllMonoGenreByIds(dto.getGenresId()));

        Mono<BookDto> result = Mono.zip(dtoMono, authorMono, genres)
                .map(t -> mapper.toEntity(t.getT1(), t.getT2(), t.getT3()))
                .flatMap(repository::save)
                .map(mapper::toDto);


        return accepted().contentType(APPLICATION_JSON).body(result, BookDto.class);
    }
}
