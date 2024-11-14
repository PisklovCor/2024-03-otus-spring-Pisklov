package ru.otus.hw.mappers;

import org.springframework.stereotype.Component;
import ru.otus.hw.dto.account.AccountDto;
import ru.otus.hw.models.Account;

@Component
public class AccountMapper {

    public AccountDto toDto(Account entity) {
        AccountDto dto = new AccountDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setSurname(entity.getSurname());
        dto.setLogin(entity.getLogin());
        dto.setMail(entity.getMail());
        return  dto;
    }
}
