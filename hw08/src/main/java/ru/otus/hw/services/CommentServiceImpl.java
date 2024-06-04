package ru.otus.hw.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.hw.converters.CommentConverter;
import ru.otus.hw.dto.CommentDto;
import ru.otus.hw.exceptions.EntityNotFoundException;
import ru.otus.hw.models.Comment;
import ru.otus.hw.repositories.BookRepository;
import ru.otus.hw.repositories.CommentRepository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CommentServiceImpl implements CommentService {

    private final CommentConverter commentConverter;

    private final CommentRepository commentRepository;

    private final BookRepository bookRepository;

    @Override
    public Optional<CommentDto> findById(String id) {
        return commentRepository.findById(String.valueOf(id)).stream().map(commentConverter::toDto).findAny();
    }

    @Override
    public List<CommentDto> findAllByBookId(String bookId) {
        return commentRepository.findAllByBookId(bookId).stream().map(commentConverter::toDto).toList();
    }

    @Override
    public CommentDto create(String content, String bookId) {
        return save(null, content, bookId);
    }

    @Override
    public CommentDto update(String id, String content) {
        var comment = commentRepository.findById(String.valueOf(id))
                .orElseThrow(() -> new EntityNotFoundException("Comment with id %s not found".formatted(id)));

        comment.setContent(content);
        return commentConverter.toDto(commentRepository.save(comment));
    }

    @Override
    public void deleteById(String id) {
        commentRepository.deleteById(String.valueOf(id));
    }

    private CommentDto save(String id, String content, String bookId) {
        var book = bookRepository.findById(bookId)
                .orElseThrow(() -> new EntityNotFoundException("Book with id %s not found".formatted(bookId)));

        var comment = new Comment(id, content, book);
        return commentConverter.toDto(commentRepository.save(comment));
    }
}
