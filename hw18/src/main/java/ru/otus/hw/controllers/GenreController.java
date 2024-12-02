package ru.otus.hw.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import ru.otus.hw.dto.GenreDto;
import ru.otus.hw.mappers.GenreMapper;
import ru.otus.hw.repositories.GenreRepository;

@RestController
@RequiredArgsConstructor
public class GenreController {

    private final GenreRepository repository;

    private final GenreMapper mapper;

    @Transactional(readOnly = true)
    @GetMapping("/api/v1/genre")
    @ResponseStatus(HttpStatus.OK)
    public Flux<GenreDto> getListGenre() {
        return repository.findAll().map(mapper::toDto);
    }
}
