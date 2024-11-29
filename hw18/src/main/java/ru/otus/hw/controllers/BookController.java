package ru.otus.hw.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import ru.otus.hw.dto.BookDto;
import ru.otus.hw.mappers.BookMapper;
import ru.otus.hw.repositories.BookRepository;

@RestController
@RequiredArgsConstructor
public class BookController {

    private final BookRepository repository;

    private final BookMapper mapper;

    @Transactional(readOnly = true)
    @GetMapping("/api/v1/book")
    @ResponseStatus(HttpStatus.OK)
    public Flux<BookDto> getListBook() {
        return repository.findAll().map(mapper::toDto);
    }
}
