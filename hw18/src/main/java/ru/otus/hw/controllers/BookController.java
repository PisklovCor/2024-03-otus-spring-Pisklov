package ru.otus.hw.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.hw.dto.BookCreateDto;
import ru.otus.hw.dto.BookDto;
import ru.otus.hw.dto.BookUpdateDto;
import ru.otus.hw.exceptions.NotFoundException;
import ru.otus.hw.mappers.BookMapper;
import ru.otus.hw.repositories.BookRepository;
import ru.otus.hw.services.DataAcquisitionService;

@RestController
@RequiredArgsConstructor
public class BookController {

    private final BookRepository repository;

    private final BookMapper mapper;

    private final DataAcquisitionService data;

    @GetMapping("/api/v1/book")
    @ResponseStatus(HttpStatus.OK)
    public Flux<BookDto> getListBook() {
        return repository.findAll().map(mapper::toDto);
    }

    @PostMapping("/api/v1/book")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<BookDto> createBook(@RequestBody @Valid BookCreateDto dto) {

        return Mono.zip(Mono.just(dto), data.findMonoAuthorById(dto.getAuthorId()),
                        data.findAllMonoGenreByIds(dto.getGenresId()))
                .map(t -> mapper.toEntity(t.getT1(), t.getT2(), t.getT3()))
                .flatMap(repository::save)
                .map(mapper::toDto);
    }

    @PutMapping("/api/v1/book")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Mono<BookDto> updateBook(@RequestBody @Valid BookUpdateDto dto) {

        var dtoMono = repository.findById(dto.getId())
                .switchIfEmpty(Mono.create(emitter -> emitter.error(
                        new NotFoundException("Book with id %s not found".formatted(dto.getId())))))
                .flatMap(b -> Mono.just(dto));

        return Mono.zip(dtoMono, data.findMonoAuthorById(dto.getAuthorId()),
                        data.findAllMonoGenreByIds(dto.getGenresId()))
                .map(t -> mapper.toEntity(t.getT1(), t.getT2(), t.getT3()))
                .flatMap(repository::save)
                .map(mapper::toDto);
    }

    @DeleteMapping("/api/v1/book/{bookId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteBook(@PathVariable(name = "bookId") String bookId) {
        return repository.deleteById(bookId);
    }
}
