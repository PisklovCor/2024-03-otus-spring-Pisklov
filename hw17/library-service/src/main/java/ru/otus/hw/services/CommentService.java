package ru.otus.hw.services;

import ru.otus.hw.dto.library.CommentDto;

import java.util.List;

public interface CommentService {

    CommentDto findById(long id);

    List<CommentDto> findAllByBookId(long bookId);

    CommentDto create(String content, long bookId);

    CommentDto update(long id, String content);

    void deleteById(long id);
}
