package ru.otus.hw.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw.clients.OrderClient;
import ru.otus.hw.dto.account.AccountCreateDto;
import ru.otus.hw.dto.account.AccountDto;
import ru.otus.hw.dto.account.AccountUpdateDto;
import ru.otus.hw.dto.order.OrderDto;
import ru.otus.hw.exceptions.NotFoundException;
import ru.otus.hw.mappers.AccountMapper;
import ru.otus.hw.models.Account;
import ru.otus.hw.repositories.AccountRepository;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class AccountServiceImpl implements AccountService {

    private final AccountMapper mapper;

    private final AccountRepository repository;

    private final OrderClient client;

    @Override
    @Transactional(readOnly = true)
    public List<AccountDto> findAll() {
        return repository.findAll().stream().map(mapper::toDto).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public AccountDto findAllByLogin(String login) {

        return mapper.toDto(repository.findAllByLogin(login)
                .orElseThrow(() -> new NotFoundException("Account with login %s not found".formatted(login))));
    }

    @Override
    @Transactional
    public AccountDto create(AccountCreateDto dto) {
        return mapper.toDto(repository.save(
                new Account(null, LocalDateTime.now(), null,
                        dto.getName(), dto.getSurname(), dto.getLogin(), dto.getMail())));
    }

    @Override
    @Transactional
    public AccountDto update(AccountUpdateDto dto) {
        final long accountId = dto.getId();

        var orderEntity = repository.findById(accountId)
                .orElseThrow(() -> new NotFoundException("Account with id %d not found".formatted(accountId)));

        orderEntity.setLastModifiedDate(LocalDateTime.now());
        orderEntity.setName(dto.getName());
        orderEntity.setSurname(dto.getSurname());
        orderEntity.setLogin(dto.getLogin());
        orderEntity.setMail(dto.getMail());

        return mapper.toDto(repository.save(orderEntity));
    }

    @Override
    @Transactional
    public void deleteById(long id) {
        repository.deleteById(id);
    }

    //todo: так же переделать на фасад для пользовательских действий с проверкой ллогина
    @Override
    public List<OrderDto> getAllOrderByLogin(String login) {

        AccountDto accountDto = mapper.toDto(repository.findAllByLogin(login)
                .orElseThrow(() -> new NotFoundException("Account with login %s not found".formatted(login))));

        return client.getOrderByLogin(accountDto.getLogin());
    }
}
