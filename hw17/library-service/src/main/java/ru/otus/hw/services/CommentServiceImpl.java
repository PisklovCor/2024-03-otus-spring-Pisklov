package ru.otus.hw.services;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw.dto.library.CommentCreateDto;
import ru.otus.hw.dto.library.CommentDto;
import ru.otus.hw.dto.library.CommentUpdateDto;
import ru.otus.hw.mappers.CommentMapper;
import ru.otus.hw.exceptions.NotFoundException;
import ru.otus.hw.models.Comment;
import ru.otus.hw.repositories.BookRepository;
import ru.otus.hw.repositories.CommentRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CommentServiceImpl implements CommentService {

    private final CommentMapper mapper;

    private final CommentRepository repository;

    private final BookRepository bookRepository;

    @Override
    @Transactional(readOnly = true)
    public CommentDto findById(long id) {
        return repository.findById(id).stream()
                .map(mapper::toDto)
                .findAny()
                .orElseThrow(() -> new NotFoundException("Comment with id %d not found".formatted(id)));
    }

    @Override
    @Transactional(readOnly = true)
    public List<CommentDto> findAllByLogin(String login) {
        return repository.findAllByLogin(login).stream().map(mapper::toDto).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<CommentDto> findAllByBookId(long bookId) {
        return repository.findAllByBookId(bookId).stream().map(mapper::toDto).toList();
    }

    @Override
    @Transactional
    public CommentDto create(CommentCreateDto dto) {

        val bookId = dto.getBookId();
        val book = bookRepository.findById(bookId)
                .orElseThrow(() -> new NotFoundException("Book with id %d not found".formatted(bookId)));

        return mapper.toDto(repository.save(new Comment(null, dto.getLogin(), dto.getContent(), book)));
    }

    @Override
    @Transactional
    public CommentDto update(CommentUpdateDto dto) {

        val commentId = dto.getId();
        val bookId = dto.getBookId();

        val comment = repository.findById(commentId)
                .orElseThrow(() -> new NotFoundException("Comment with id %d not found".formatted(commentId)));

        val book = bookRepository.findById(bookId)
                .orElseThrow(() -> new NotFoundException("Book with id %d not found".formatted(bookId)));

        comment.setLogin(dto.getLogin());
        comment.setContent(dto.getContent());
        comment.setBook(book);
        return mapper.toDto(repository.save(comment));
    }

    @Override
    @Transactional
    public void deleteById(long id) {
        repository.deleteById(id);
    }
}
