package ru.otus.hw.mappers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.hw.dto.library.AuthorDto;
import ru.otus.hw.dto.library.BookDto;
import ru.otus.hw.dto.library.GenreDto;
import ru.otus.hw.models.Book;

import java.util.List;

@RequiredArgsConstructor
@Component
public class BookMapper {

    private final AuthorMapper authorMapper;

    private final GenreMapper genreMapper;

    public BookDto toDto(Book entity) {
        AuthorDto authorDto = authorMapper.toDto(entity.getAuthor());
        List<GenreDto> genreDtoList = entity.getGenres().stream().map(genreMapper::toDto).toList();
        BookDto dto = new BookDto();
        dto.setId(entity.getId());
        dto.setTitle(entity.getTitle());
        dto.setAuthor(authorDto);
        dto.setGenres(genreDtoList);
        return  dto;
    }
}
