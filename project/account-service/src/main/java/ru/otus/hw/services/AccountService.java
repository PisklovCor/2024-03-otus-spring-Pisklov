package ru.otus.hw.services;

import ru.otus.hw.dto.account.AccountCreateDto;
import ru.otus.hw.dto.account.AccountDto;
import ru.otus.hw.dto.account.AccountUpdateDto;

import java.util.List;

public interface AccountService {

    List<AccountDto> findAll();

    AccountDto findAllByLogin(String login);

    AccountDto create(AccountCreateDto dto);

    AccountDto update(AccountUpdateDto dto);

    void deleteById(long id);

}
