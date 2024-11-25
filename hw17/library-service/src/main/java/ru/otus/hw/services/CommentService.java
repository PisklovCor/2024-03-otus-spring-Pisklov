package ru.otus.hw.services;

import ru.otus.hw.dto.library.CommentCreateDto;
import ru.otus.hw.dto.library.CommentDto;
import ru.otus.hw.dto.library.CommentUpdateDto;

import java.util.List;

public interface CommentService {

    CommentDto findById(long id);

    List<CommentDto> findAllByLogin(String login);

    List<CommentDto> findAllByBookId(long bookId);

    CommentDto create(CommentCreateDto dto);

    CommentDto update(CommentUpdateDto dto);

    void deleteById(long id);
}
