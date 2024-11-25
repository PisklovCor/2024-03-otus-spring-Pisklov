package ru.otus.hw.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.otus.hw.dto.library.AuthorDto;
import ru.otus.hw.services.AuthorService;

import java.util.List;

@Tag(name = "Контроллер авторов", description = "Контроллер взаимодействия с авторами")
@RestController
@RequiredArgsConstructor
public class AuthorController {

    private final AuthorService authorService;

    @Operation(
            summary = "Получение авторов",
            description = "Позволяет получить список всех существующих авторов"
    )
    @GetMapping("/api/v1/author")
    @ResponseStatus(HttpStatus.OK)
    public List<AuthorDto> getListAuthor() {
        return authorService.findAll();
    }
}
