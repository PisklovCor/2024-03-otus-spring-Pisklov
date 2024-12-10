package ru.otus.hw.services;

import ru.otus.hw.dto.account.AccountBookDto;
import ru.otus.hw.dto.account.AccountBookCreateDto;
import ru.otus.hw.dto.account.AccountBookUpdateDto;

import java.util.List;

public interface AccountBookService {

    List<AccountBookDto> findAll();

    List<AccountBookDto> findAllByLogin(String login);

    AccountBookDto create(AccountBookCreateDto dto);

    AccountBookDto update(AccountBookUpdateDto dto);

    void deleteById(long id);

}
