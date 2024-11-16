package ru.otus.hw.mappers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.hw.dto.BookDto;
import ru.otus.hw.dto.account.AccountBookDto;
import ru.otus.hw.models.AccountBook;

@Component
@RequiredArgsConstructor
public class AccountBooKMapper {

    private final AccountMapper accountMapper;

    public AccountBookDto toDto(AccountBook entity) {
        BookDto bookDtoEmpty = new BookDto();
        bookDtoEmpty.setId(entity.getBookId());

        AccountBookDto dto = new AccountBookDto();
        dto.setId(entity.getId());
        dto.setAccount(accountMapper.toDto(entity.getAccount()));
        dto.setBook(bookDtoEmpty);
        return  dto;
    }
}
