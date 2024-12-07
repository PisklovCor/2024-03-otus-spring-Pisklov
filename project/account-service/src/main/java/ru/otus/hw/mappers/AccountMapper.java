package ru.otus.hw.mappers;

import org.springframework.stereotype.Component;
import ru.otus.hw.dto.account.AccountDto;
import ru.otus.hw.models.Account;

@Component
public class AccountMapper {

    public AccountDto toDto(Account entity) {
        return AccountDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .surname(entity.getSurname())
                .login(entity.getLogin())
                .mail(entity.getMail())
                .build();
    }
}
