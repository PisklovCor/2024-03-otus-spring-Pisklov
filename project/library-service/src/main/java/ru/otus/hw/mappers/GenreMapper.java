package ru.otus.hw.mappers;

import org.springframework.stereotype.Component;
import ru.otus.hw.dto.library.GenreDto;
import ru.otus.hw.models.Genre;

@Component
public class GenreMapper {

    public GenreDto toDto(Genre entity) {
        GenreDto dto = new GenreDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        return  dto;
    }
}
