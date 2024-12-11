package ru.otus.hw.services;

import ru.otus.hw.dto.library.BookCreateDto;
import ru.otus.hw.dto.library.BookDto;
import ru.otus.hw.dto.library.BookUpdateDto;
import ru.otus.hw.dto.account.AccountBookDto;
import ru.otus.hw.dto.order.OrderDto;

import java.util.List;

public interface BookService {

    List<BookDto> findAll();

    BookDto findById(long id);

    BookDto create(BookCreateDto dto);

    BookDto update(BookUpdateDto dto);

    void deleteById(long id);

    OrderDto leaveBookOrder(String bookTitle, String login);

    AccountBookDto takeBook(long id, String login);

}
