package ru.otus.hw.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import ru.otus.hw.dto.account.AccountBookDto;
import ru.otus.hw.dto.account.AccountBookUpdateDto;
import ru.otus.hw.dto.account.AccountBookCreateDto;
import ru.otus.hw.services.AccountBookFacade;
import ru.otus.hw.services.AccountBookService;

import java.util.List;

@Tag(name = "Контроллер связи аккаунтов с книгами",
        description = "Контроллер взаимодействия со связями аккаунтов с книгами")
@RestController
@RequestMapping("/account-service")
@RequiredArgsConstructor
public class AccountBookController {

    private final AccountBookService service;

    private final AccountBookFacade facade;

    @Operation(
            summary = "Получение связи аккаунтов с книгами",
            description = "Позволяет получить список всех связей аккаунтов с книгами"
    )
    @GetMapping("/api/v1/account/book")
    @ResponseStatus(HttpStatus.OK)
    public List<AccountBookDto> getListAccountBook() {
        return facade.findAll();
    }

    @Operation(
            summary = "Получение связи аккаунта с книгой по логину",
            description = "Позволяет получить связь аккаунта с книгой по логину пользователя"
    )
    @GetMapping("/api/v1/account/book/{login}")
    @ResponseStatus(HttpStatus.OK)
    public List<AccountBookDto> gteAccountBookByLogin(@PathVariable("login")
                                                  @Parameter(description = "Логин пользователя",
                                                          example = "guest") String login) {
        return facade.findAllByLogin(login);
    }

    @Operation(
            summary = "Создание связи аккаунта с книгой",
            description = "Позволяет создать связь аккаунта с книгой"
    )
    @PostMapping("/api/v1/account/book")
    @ResponseStatus(HttpStatus.CREATED)
    public AccountBookDto createAccountBook(@Valid @RequestBody AccountBookCreateDto accountBookCreateDto) {
        return facade.create(accountBookCreateDto);
    }

    @Operation(
            summary = "Обновление связи аккаунтов с книгами",
            description = "Позволяет обновить существующую связь аккаунта с книгой"
    )
    @PutMapping("/api/v1/account/book")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public AccountBookDto updateAccountBook(@Valid @RequestBody AccountBookUpdateDto accountBookUpdateDto) {
        return facade.update(accountBookUpdateDto);
    }

    @Operation(
            summary = "Удаление связи аккаунта с книгой",
            description = "Позволяет удалить существующую связь аккаунта с книгой"
    )
    @DeleteMapping("/api/v1/account/book/{accountBookId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAccountBook(@PathVariable("accountBookId") long accountBookId) {
        service.deleteById(accountBookId);
    }

}
