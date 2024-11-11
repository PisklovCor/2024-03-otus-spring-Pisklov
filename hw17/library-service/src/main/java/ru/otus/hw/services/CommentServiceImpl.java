package ru.otus.hw.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw.dto.CommentDto;
import ru.otus.hw.mappers.CommentMapper;
import ru.otus.hw.exceptions.NotFoundException;
import ru.otus.hw.models.Comment;
import ru.otus.hw.repositories.BookRepository;
import ru.otus.hw.repositories.CommentRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CommentServiceImpl implements CommentService {

    private final CommentMapper commentMapper;

    private final CommentRepository commentRepository;

    private final BookRepository bookRepository;

    @Override
    @Transactional(readOnly = true)
    public CommentDto findById(long id) {
        return commentRepository.findById(id).stream()
                .map(commentMapper::toDto)
                .findAny()
                .orElseThrow(() -> new NotFoundException("Comment with id %d not found".formatted(id)));
    }

    @Override
    @Transactional(readOnly = true)
    public List<CommentDto> findAllByBookId(long bookId) {
        return commentRepository.findAllByBookId(bookId).stream().map(commentMapper::toDto).toList();
    }

    @Override
    @Transactional
    public CommentDto create(String content, long bookId) {
        return save(null, content, bookId);
    }

    @Override
    @Transactional
    public CommentDto update(long id, String content) {
        var comment = commentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Comment with id %d not found".formatted(id)));

        comment.setContent(content);
        return commentMapper.toDto(commentRepository.save(comment));
    }

    @Override
    @Transactional
    public void deleteById(long id) {
        commentRepository.deleteById(id);
    }

    private CommentDto save(Long id, String content, long bookId) {
        var book = bookRepository.findById(bookId)
                .orElseThrow(() -> new NotFoundException("Book with id %d not found".formatted(bookId)));

        var comment = new Comment(id, content, book);
        return commentMapper.toDto(commentRepository.save(comment));
    }
}
