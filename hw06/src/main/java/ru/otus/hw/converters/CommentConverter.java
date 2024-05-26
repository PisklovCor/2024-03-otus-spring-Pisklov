package ru.otus.hw.converters;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.hw.dto.BookDto;
import ru.otus.hw.dto.CommentDto;
import ru.otus.hw.models.Comment;

@RequiredArgsConstructor
@Component
public class CommentConverter {

    private final BookConverter bookConverter;

    public String commentToString(CommentDto comment) {
        var bookString = bookConverter.bookToString(comment.getBook());
        return "Id: %d, Content: %s, Book: {%s}".formatted(
                comment.getId(),
                comment.getContent(),
                bookString);
    }

    public CommentDto toDto(Comment entity) {
        BookDto bookDto = bookConverter.toDto(entity.getBook());
        CommentDto dto = new CommentDto();
        dto.setId(entity.getId());
        dto.setContent(entity.getContent());
        dto.setBook(bookDto);
        return  dto;
    }

}
