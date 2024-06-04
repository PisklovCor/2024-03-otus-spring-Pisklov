package ru.otus.hw.converters;

import org.springframework.stereotype.Component;
import ru.otus.hw.dto.AuthorDto;
import ru.otus.hw.models.Author;

@Component
public class AuthorConverter {
    public String authorToString(AuthorDto author) {
        return "Id: %s, Name: %s".formatted(author.getId(), author.getFullName());
    }

    public AuthorDto toDto(Author entity) {
        AuthorDto dto = new AuthorDto();
        dto.setId(entity.getId());
        dto.setFullName(entity.getName());
        return  dto;
    }
}
