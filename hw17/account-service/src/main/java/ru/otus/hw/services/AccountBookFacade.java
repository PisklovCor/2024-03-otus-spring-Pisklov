package ru.otus.hw.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.stereotype.Service;
import ru.otus.hw.clients.LibraryClient;
import ru.otus.hw.dto.BookDto;
import ru.otus.hw.dto.account.AccountBookCreateDto;
import ru.otus.hw.dto.account.AccountBookDto;
import ru.otus.hw.dto.account.AccountBookUpdateDto;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class AccountBookFacade {

    private final AccountBookService service;

    private final LibraryClient client;

    public List<AccountBookDto> findAll() {

        var accountBookDtoList = service.findAll();

        for (AccountBookDto accountBookDto : accountBookDtoList) {

            val bookId = accountBookDto.getBook().getId();
            log.info("Searching by book ID=[{}]", bookId);
            val bookDto = client.getBookById(bookId);

            accountBookDto.setBook(bookDto);
        }

        return accountBookDtoList;
    }

    public List<AccountBookDto> findAllByLogin(String login) {

        var accountBookDtoList = service.findAllByLogin(login);

        for (AccountBookDto accountBookDto : accountBookDtoList) {
            accountBookDto.setBook(gettingBookFromExternalSystemByIdentifier(accountBookDto.getBook().getId()));
        }

        return accountBookDtoList;
    }

    public AccountBookDto create(AccountBookCreateDto dto) {

        var accountBookDto = service.create(dto);
        accountBookDto.setBook(gettingBookFromExternalSystemByIdentifier(accountBookDto.getBook().getId()));
        return accountBookDto;
    }

    public AccountBookDto update(AccountBookUpdateDto dto) {

        var accountBookDto = service.update(dto);
        accountBookDto.setBook(gettingBookFromExternalSystemByIdentifier(accountBookDto.getBook().getId()));
        return accountBookDto;
    }

    private BookDto gettingBookFromExternalSystemByIdentifier(long bookId) {
        log.info("Searching by book ID=[{}]", bookId);
        val bookDto = client.getBookById(bookId);
        log.info("Book found=[{}]", bookDto);
        return bookDto;
    }
}
