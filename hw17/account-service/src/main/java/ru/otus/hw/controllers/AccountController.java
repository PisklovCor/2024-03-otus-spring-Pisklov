package ru.otus.hw.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import ru.otus.hw.dto.account.AccountCreateDto;
import ru.otus.hw.dto.account.AccountDto;
import ru.otus.hw.dto.account.AccountUpdateDto;
import ru.otus.hw.dto.notification.MessageUserDto;
import ru.otus.hw.dto.order.OrderDto;
import ru.otus.hw.services.AccountFacade;
import ru.otus.hw.services.AccountService;

import java.util.List;

@Tag(name = "Контроллер аккаунтов", description = "Контроллер взаимодействия с аккаунтами пользователей")
@RestController
@RequiredArgsConstructor
public class AccountController {

    private final AccountService service;

    private final AccountFacade facade;

    @Operation(
            summary = "Получение аккаунтов",
            description = "Позволяет получить список всех существующих аккаунтов пользователей"
    )
    @GetMapping("/api/v1/account")
    @ResponseStatus(HttpStatus.OK)
    public List<AccountDto> getListAccount() {
        return service.findAll();
    }

    @Operation(
            summary = "Получение аккаунта по логину",
            description = "Позволяет получить информаци об аккаунте по логину пользователя"
    )
    @GetMapping("/api/v1/account/{login}")
    @ResponseStatus(HttpStatus.OK)
    public AccountDto gteAccountByLogin(@PathVariable("login")
                                        @Parameter(description = "Логин пользователя", example = "guest") String login) {
        return service.findAllByLogin(login);
    }

    @Operation(
            summary = "Создание аккаунта",
            description = "Позволяет создать новый аккаунт"
    )
    @PostMapping("/api/v1/account")
    @ResponseStatus(HttpStatus.CREATED)
    public AccountDto createAccount(@Valid @RequestBody AccountCreateDto accountCreateDto) {
        return service.create(accountCreateDto);
    }

    @Operation(
            summary = "Обновление аккаунта",
            description = "Позволяет обновить существующий аккаунт пользователя"
    )
    @PutMapping("/api/v1/account")
    @ResponseStatus(HttpStatus.OK)
    public AccountDto updateAccount(@Valid @RequestBody AccountUpdateDto accountUpdateDto) {
        return service.update(accountUpdateDto);
    }

    @Operation(
            summary = "Удаление аккаунта заказа",
            description = "Позволяет удалить существующий аккаунт по ID"
    )
    @DeleteMapping("/api/v1/account/{accountId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void deleteAccount(@PathVariable("accountId") long accountId) {
        service.deleteById(accountId);
    }

    @Operation(
            summary = "Получение заказов",
            description = "Позволяет получить все заказы пользователя по его лоигину"
    )
    @GetMapping("/api/v1/account/order/{login}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public List<OrderDto> getAllOrderByLogin(@PathVariable("login") String login) {
        return facade.getAllOrderByLogin(login);
    }

    @Operation(
            summary = "Получение уведомлений",
            description = "Позволяет получить все уведомления пользователя по его лоигину"
    )
    @GetMapping("/api/v1/account/notification/{login}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public List<MessageUserDto> getAllNotificationByLogin(@PathVariable("login") String login) {
        return facade.getAllNotificationByLogin(login);
    }
}
