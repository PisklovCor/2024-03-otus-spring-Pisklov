package ru.otus.hw.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw.dto.account.AccountBookCreateDto;
import ru.otus.hw.dto.account.AccountBookDto;
import ru.otus.hw.dto.account.AccountBookUpdateDto;
import ru.otus.hw.exceptions.NotFoundException;
import ru.otus.hw.mappers.AccountBooKMapper;
import ru.otus.hw.models.Account;
import ru.otus.hw.models.AccountBook;
import ru.otus.hw.repositories.AccountBookRepository;
import ru.otus.hw.repositories.AccountRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountBookServiceImpl implements AccountBookService {

    private final AccountBooKMapper mapper;

    private final AccountBookRepository repository;

    private final AccountRepository accountRepository;


    @Override
    @Transactional(readOnly = true)
    public List<AccountBookDto> findAll() {
        return repository.findAll().stream().map(mapper::toDto).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<AccountBookDto> findAllByLogin(String login) {

        final Account account = accountFindByLogin(login);

        return repository.findAllByAccount(account).stream().map(mapper::toDto).toList();
    }

    @Override
    @Transactional
    public AccountBookDto create(AccountBookCreateDto dto) {

        var login = dto.getLogin();
        final Account account = accountFindByLogin(login);

        return mapper.toDto(repository.save(
                new AccountBook(null, LocalDateTime.now(), null,
                        account, dto.getBookId())));
    }

    @Override
    @Transactional
    public AccountBookDto update(AccountBookUpdateDto dto) {

        final long accountBookId = dto.getId();

        var accountBookEntity = repository.findById(accountBookId)
                .orElseThrow(() -> new NotFoundException("AccountBook with id %d not found".formatted(accountBookId)));

        var login = dto.getLogin();
        final Account account = accountFindByLogin(login);

        accountBookEntity.setLastModifiedDate(LocalDateTime.now());
        accountBookEntity.setAccount(account);
        accountBookEntity.setBookId(dto.getBookId());

        return mapper.toDto(repository.save(accountBookEntity));
    }

    @Override
    @Transactional
    public void deleteById(long id) {
        repository.deleteById(id);
    }

    private Account accountFindByLogin(String login) {
        return accountRepository.findAllByLogin(login)
                .orElseThrow(() -> new NotFoundException("Account with login %s not found".formatted(login)));
    }
}
