package ru.otus.hw.mappers;

import org.springframework.stereotype.Component;
import ru.otus.hw.dto.library.AuthorDto;
import ru.otus.hw.models.Author;

@Component
public class AuthorMapper {

    public AuthorDto toDto(Author entity) {
        AuthorDto dto = new AuthorDto();
        dto.setId(entity.getId());
        dto.setFullName(entity.getFullName());
        return  dto;
    }
}
