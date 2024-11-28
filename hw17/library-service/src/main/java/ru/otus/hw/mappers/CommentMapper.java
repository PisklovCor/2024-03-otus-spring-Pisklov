package ru.otus.hw.mappers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.hw.dto.library.CommentDto;
import ru.otus.hw.models.Comment;

@RequiredArgsConstructor
@Component
public class CommentMapper {

    public CommentDto toDto(Comment entity) {
        return CommentDto.builder()
                .id(entity.getId())
                .login(entity.getLogin())
                .content(entity.getContent())
                .build();
    }
}
