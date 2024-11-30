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
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Genre;
import ru.otus.hw.repositories.AuthorRepository;
import ru.otus.hw.repositories.BookRepository;
import ru.otus.hw.repositories.GenreRepository;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BookController {

    private final BookRepository repository;

    private final BookMapper mapper;

    private final AuthorRepository authorRepository;

    private final GenreRepository genreRepository;

    @GetMapping("/api/v1/book")
    @ResponseStatus(HttpStatus.OK)
    public Flux<BookDto> getListBook() {
        return repository.findAll().map(mapper::toDto);
    }

    @PostMapping("/api/v1/book")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<BookDto> createBook(@RequestBody @Valid BookCreateDto dto) {

        return Mono.zip(Mono.just(dto), findMonoAuthorById(dto.getAuthorId()), findAllMonoGenreByIds(dto.getGenresId()))
                .map(t -> mapper.toEntity(t.getT1(), t.getT2(), t.getT3()))
                .flatMap(repository::save)
                .map(mapper::toDto);
    }

    @PutMapping("/api/v1/book")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Mono<BookDto> updateBook(@RequestBody @Valid BookUpdateDto dto) {

        var bookMono = repository.findById(dto.getId())
                .switchIfEmpty(Mono.create(emitter -> emitter.error(
                        new NotFoundException("Book with id %s not found".formatted(dto.getId())))))
                .flatMap(b -> Mono.just(dto));

        return Mono.zip(bookMono, findMonoAuthorById(dto.getAuthorId()), findAllMonoGenreByIds(dto.getGenresId()))
                .map(t -> mapper.toEntity(t.getT1(), t.getT2(), t.getT3()))
                .flatMap(repository::save)
                .map(mapper::toDto);
    }

    @DeleteMapping("/api/v1/book/{bookId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteBook(@PathVariable(name = "bookId") String bookId) {
        //return repository.deleteById(bookId);

        return repository.findById(bookId)
                .flatMap(repository::delete);
    }

    private Mono<Author> findMonoAuthorById(String authorId) {
        return authorRepository.findById(authorId)
                .switchIfEmpty(Mono.create(emitter -> emitter.error(
                        new NotFoundException("Author with id %s not found".formatted(authorId)))));
    }

    private Mono<List<Genre>> findAllMonoGenreByIds(List<String> genresIds) {
        return genreRepository.findAllById(genresIds)
                .collectList()
                .flatMap(list -> {
                    if (list.size() != genresIds.size()) {
                        return Mono.create(emitter -> emitter.error(
                                new NotFoundException("Not all genres found from list %s"
                                        .formatted(genresIds))));
                    } else {
                        return Mono.just(list);
                    }
                });
    }
}
