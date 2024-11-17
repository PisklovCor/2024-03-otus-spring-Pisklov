package ru.otus.hw.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.otus.hw.dto.order.OrderCreateDto;
import ru.otus.hw.dto.order.OrderDto;
import ru.otus.hw.dto.order.OrderUpdateDto;
import ru.otus.hw.services.OrderFacade;
import ru.otus.hw.services.OrderService;

import java.util.List;

@Tag(name = "Контроллер заказов", description = "Контроллер взаимодействия с заказами")
@RestController
@RequiredArgsConstructor
public class OrderController {

    private final OrderService service;

    private final OrderFacade facade;

    @Operation(
            summary = "Получение заказов",
            description = "Позволяет получить список всех существующих заказов"
    )
    @GetMapping("/api/v1/order")
    @ResponseStatus(HttpStatus.OK)
    public List<OrderDto> getListOrder() {
        return service.findAll();
    }

    @Operation(
            summary = "Получение заказов по логину",
            description = "Позволяет получить список заказов по логину пользователя"
    )
    @GetMapping("/api/v1/order/{login}")
    @ResponseStatus(HttpStatus.OK)
    public List<OrderDto> gteListOrderByLogin(@PathVariable("login")
                               @Parameter(description = "Логин пользователя", example = "guest") String login) {
        return service.findAllByLogin(login);
    }

    @Operation(
            summary = "Создание заказа",
            description = "Позволяет создать новый заказ со статусом CREATED"
    )
    @PostMapping("/api/v1/order")
    @ResponseStatus(HttpStatus.CREATED)
    public OrderDto createOrder(@Valid @RequestBody OrderCreateDto orderCreateDto) {
        return facade.createAndSendMessage(orderCreateDto);
    }

    @Operation(
            summary = "Обновление заказа",
            description = "Позволяет обновить существующий заказ"
    )
    @PutMapping("/api/v1/order")
    @ResponseStatus(HttpStatus.OK)
    public OrderDto updateOrder(@Valid @RequestBody OrderUpdateDto orderUpdateDto) {
        return facade.updateAndSendMessage(orderUpdateDto);
    }
}
