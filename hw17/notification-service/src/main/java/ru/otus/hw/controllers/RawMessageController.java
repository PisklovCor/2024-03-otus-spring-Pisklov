package ru.otus.hw.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RequestBody;
import ru.otus.hw.dto.notification.RawMessageCreateDto;
import ru.otus.hw.dto.notification.RawMessageDto;
import ru.otus.hw.services.RawMessageService;

import java.util.List;

@Tag(name = "Контроллер сырых сообщений", description = "Контроллер взаимодействия с сырыми сообщениями")
@RestController
@RequiredArgsConstructor
public class RawMessageController {

    private final RawMessageService service;

    @Operation(
            summary = "Получение сырых сообщений",
            description = "Позволяет получить список всех существующих сырых сообщений"
    )
    @GetMapping("/api/v1/raw-message")
    @ResponseStatus(HttpStatus.OK)
    public List<RawMessageDto> getListRawMessage() {
        return service.findAll();
    }

    @Operation(
            summary = "Создание сырого сообщения",
            description = "Позволяет создать новое сырое сообщение"
    )
    @PostMapping("/api/v1/raw-message")
    @ResponseStatus(HttpStatus.CREATED)
    public RawMessageDto createRawMessage(@Valid @RequestBody RawMessageCreateDto dto) {
        return service.create(dto);
    }
}
