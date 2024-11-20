package ru.otus.hw.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.hw.dto.notification.MessageUserDto;
import ru.otus.hw.services.MessageUserService;

import java.util.List;

@Tag(name = "Контроллер сообщений", description = "Контроллер сообщений")
@RestController
@RequiredArgsConstructor
public class MessageUserController {

    private final MessageUserService service;

    @Operation(
            summary = "Получить все сообщения",
            description = "Позволяет получить все сообщения"
    )
    @GetMapping("/api/v1/user-message")
    @ResponseStatus(HttpStatus.OK)
    public List<MessageUserDto> getAllMessageUser() {
        return service.findAll();
    }

    @Operation(
            summary = "Получение сообщений пользоваетля",
            description = "Позволяет получить все сообщения пользователя по логину"
    )
    @GetMapping("/api/v1/user-message/{login}")
    @ResponseStatus(HttpStatus.OK)
    public List<MessageUserDto> getAllMessageUserByLogin(@PathVariable("login")
                                                         @Parameter(description = "Логин пользователя",
                                                                 example = "guest") String login) {
        return service.findAllByLogin(login);
    }
}
