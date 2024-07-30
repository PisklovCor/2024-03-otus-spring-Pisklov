package ru.otus.hw.services;

import ru.otus.hw.dto.BookDto;

import java.util.List;
import java.util.Optional;

public interface BookService {

    List<BookDto> findAll();

    Optional<BookDto> findById(long id);

    BookDto create(BookDto dto);

    BookDto update(BookDto dto);

    void deleteById(long id);
}
