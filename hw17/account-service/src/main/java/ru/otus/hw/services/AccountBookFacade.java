package ru.otus.hw.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.stereotype.Service;
import ru.otus.hw.clients.LibraryClient;
import ru.otus.hw.clients.RabbitMqProducers;
import ru.otus.hw.dto.BookDto;
import ru.otus.hw.dto.account.AccountBookCreateDto;
import ru.otus.hw.dto.account.AccountBookDto;
import ru.otus.hw.dto.account.AccountBookUpdateDto;
import ru.otus.hw.jms.JmsNotificationMessage;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountBookFacade {

    private final AccountBookService service;

    private final LibraryClient client;

    private RabbitMqProducers producers;

    public List<AccountBookDto> findAll() {

        var accountBookDtoList = service.findAll();

        for (AccountBookDto accountBookDto : accountBookDtoList) {

            val bookId = accountBookDto.getBook().getId();
            log.info("Searching by book ID: {}", bookId);
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
        try {
            accountBookDto.setBook(gettingBookFromExternalSystemByIdentifier(accountBookDto.getBook().getId()));
            producers.sendingCreationMessage(makeJmsNotificationMessage(accountBookDto));
        } catch (Exception e) {
            log.error("Failed to send message [{}]", e.getMessage());
            producers.sendingErrorMessage(makeJmsNotificationMessage(accountBookDto));
        }

        return accountBookDto;
    }

    public AccountBookDto update(AccountBookUpdateDto dto) {

        var accountBookDto = service.update(dto);

        try {
            accountBookDto.setBook(gettingBookFromExternalSystemByIdentifier(accountBookDto.getBook().getId()));
            producers.sendingUpdateMessage(makeJmsNotificationMessage(accountBookDto));

        } catch (Exception e) {
            log.error("Failed to send message [{}]", e.getMessage());
            producers.sendingErrorMessage(JmsNotificationMessage.builder()
                    .login(String.valueOf(accountBookDto.getAccount().getId()))
                    .build());
        }

        return accountBookDto;
    }

    private BookDto gettingBookFromExternalSystemByIdentifier(long bookId) {
        log.info("Searching by book ID: {}", bookId);
        val bookDto = client.getBookById(bookId);
        log.info("Book found: {}", bookDto);
        return bookDto;
    }

    private JmsNotificationMessage makeJmsNotificationMessage(AccountBookDto dto) {

        val bookDto = dto.getBook();
        val bookDescription = bookDto.getTitle() + " , " + bookDto.getAuthor().getFullName();

        return JmsNotificationMessage.builder()
                .login(dto.getAccount().getLogin())
                .bookDescription(bookDescription)
                .build();
    }
}
