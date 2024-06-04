package ru.otus.hw.services;

import ru.otus.hw.dto.BookDto;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface BookService {

    List<BookDto> findAll();

    Optional<BookDto> findById(String id);

    BookDto create(String title, String authorId, Set<String> genresIds);

    BookDto update(String id, String title, String authorId, Set<String> genresIds);

    void deleteById(String id);
}
