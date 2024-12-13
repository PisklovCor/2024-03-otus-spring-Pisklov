package ru.otus.hw.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import ru.otus.hw.dto.AuthorDto;
import ru.otus.hw.mappers.AuthorMapper;
import ru.otus.hw.repositories.AuthorRepository;

@RestController
@RequiredArgsConstructor
public class AuthorController {

    private final AuthorRepository repository;

    private final AuthorMapper mapper;

    @GetMapping("/api/v1/author")
    @ResponseStatus(HttpStatus.OK)
    public Flux<AuthorDto> getListAuthor() {
        return repository.findAll().map(mapper::toDto);
    }
}
