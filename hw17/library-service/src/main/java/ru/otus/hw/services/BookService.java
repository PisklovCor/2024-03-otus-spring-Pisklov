package ru.otus.hw.services;

import ru.otus.hw.dto.BookCreateDto;
import ru.otus.hw.dto.BookDto;
import ru.otus.hw.dto.BookUpdateDto;
import ru.otus.hw.dto.account.AccountBookDto;
import ru.otus.hw.dto.order.OrderDto;

import java.util.List;

public interface BookService {

    List<BookDto> findAll();

    BookDto findById(long id);

    BookDto create(BookCreateDto dto);

    BookDto update(BookUpdateDto dto);

    void deleteById(long id);

    OrderDto leaveBookOrder(String bookTitle);

    AccountBookDto takeBook(long id);

}
