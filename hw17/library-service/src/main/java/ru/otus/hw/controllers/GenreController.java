package ru.otus.hw.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.otus.hw.dto.library.GenreDto;
import ru.otus.hw.services.GenreService;

import java.util.List;

@Tag(name = "Контроллер жанров", description = "Контроллер взаимодействия с жанрами")
@RestController
@RequestMapping("/library-service")
@RequiredArgsConstructor
public class GenreController {

    private final GenreService service;

    @Operation(
            summary = "Получение жанры",
            description = "Позволяет получить список всех существующих жанров"
    )
    @GetMapping("/api/v1/genre")
    @ResponseStatus(HttpStatus.OK)
    public List<GenreDto> getListGenre() {
        return service.findAll();
    }
}
