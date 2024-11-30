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
import ru.otus.hw.repositories.BookRepository;
import ru.otus.hw.services.DataAcquisitionService;

import java.net.URI;

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

        var dtoMono = request.bodyToMono(BookCreateDto.class);
        var authorMono = dtoMono.flatMap(dto -> data.findMonoAuthorById(dto.getAuthorId()));
        var genres = dtoMono.flatMap(dto -> data.findAllMonoGenreByIds(dto.getGenresId()));

        var result = Mono.zip(dtoMono, authorMono, genres)
                .map(t -> mapper.toEntity(t.getT1(), t.getT2(), t.getT3()))
                .flatMap(repository::save)
                .map(mapper::toDto);

        return created(URI.create("/route/v1/book/%s".formatted("1234")))
                .contentType(APPLICATION_JSON).body(result, BookDto.class);
    }

    public Mono<ServerResponse> updateBook(ServerRequest request) {

        var dtoMono = request.bodyToMono(BookUpdateDto.class);
        var authorMono = dtoMono.flatMap(dto -> data.findMonoAuthorById(dto.getAuthorId()));
        var genres = dtoMono.flatMap(dto -> data.findAllMonoGenreByIds(dto.getGenresId()));

        var result = Mono.zip(dtoMono, authorMono, genres)
                .map(t -> mapper.toEntity(t.getT1(), t.getT2(), t.getT3()))
                .flatMap(repository::save)
                .map(mapper::toDto);


        return accepted().contentType(APPLICATION_JSON).body(result, BookDto.class);
    }
}
