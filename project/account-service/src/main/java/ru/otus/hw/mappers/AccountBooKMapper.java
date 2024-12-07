package ru.otus.hw.mappers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.hw.dto.library.BookDto;
import ru.otus.hw.dto.account.AccountBookDto;
import ru.otus.hw.models.AccountBook;

@Component
@RequiredArgsConstructor
public class AccountBooKMapper {

    private final AccountMapper accountMapper;

    public AccountBookDto toDto(AccountBook entity) {

        return AccountBookDto.builder()
                .id(entity.getId())
                .account(accountMapper.toDto(entity.getAccount()))
                .book(BookDto.builder()
                        .id(entity
                        .getBookId())
                        .build())
                .build();
    }
}
