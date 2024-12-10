package ru.otus.hw.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.DeleteMapping;
import ru.otus.hw.dto.library.CommentCreateDto;
import ru.otus.hw.dto.library.CommentDto;
import ru.otus.hw.dto.library.CommentUpdateDto;
import ru.otus.hw.services.CommentFacade;
import ru.otus.hw.services.CommentService;

import java.util.List;

@Tag(name = "Контроллер комментариев", description = "Контроллер взаимодействия с комментариями")
@RestController
@RequestMapping("/library-service")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService service;

    private final CommentFacade facade;

    @Operation(
            summary = "Получение комментария по ID",
            description = "Позволяет получить комментарий по его ID"
    )
    @GetMapping("/api/v1/comment/{commentId}")
    @ResponseStatus(HttpStatus.OK)
    public CommentDto getCommentById(@PathVariable
                                     @Parameter(description = "ID комментария", example = "1") long commentId) {
        return service.findById(commentId);
    }

    @Operation(
            summary = "Получение комментариев по логину пользователя",
            description = "Позволяет получить список всех комментариев по логину пользоваетля"
    )
    @GetMapping("/api/v1/comment")
    @ResponseStatus(HttpStatus.OK)
    public List<CommentDto> getCommentByUserLogin(@RequestParam("login")
                                                  @Parameter(description = "Логин пользователя",
                                                          example = "guest") String login) {
        return service.findAllByLogin(login);
    }

    @Operation(
            summary = "Получение комментариев по ID книги",
            description = "Позволяет получить список всех комментариев по заданному ID книги"
    )
    @GetMapping("/api/v1/comment/book/{bookId}")
    @ResponseStatus(HttpStatus.OK)
    public List<CommentDto> getCommentByBookId(@PathVariable("bookId")
                                               @Parameter(description = "ID книги", example = "1") long bookId) {
        return service.findAllByBookId(bookId);
    }

    @Operation(
            summary = "Создание комментария",
            description = "Позволяет создать новый комментарий"
    )
    @PostMapping("/api/v1/comment")
    @ResponseStatus(HttpStatus.CREATED)
    public CommentDto createAccount(@Valid @RequestBody CommentCreateDto commentCreateDto) {
        return facade.create(commentCreateDto);
    }

    @Operation(
            summary = "Обновление комментария",
            description = "Позволяет обновить существующий комментарий пользователя"
    )
    @PutMapping("/api/v1/comment")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public CommentDto updateAccount(@Valid @RequestBody CommentUpdateDto commentUpdateDto) {
        return facade.update(commentUpdateDto);
    }

    @Operation(
            summary = "Удаление комментария",
            description = "Позволяет удалить комментарий по ID"
    )
    @DeleteMapping("/api/v1/comment/{commentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteComment(@PathVariable("commentId")
                              @Parameter(description = "ID комментария", example = "1") long commentId) {
        service.deleteById(commentId);
    }
}
