package ru.otus.hw.converters;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.hw.dto.AuthorDto;
import ru.otus.hw.dto.BookDto;
import ru.otus.hw.dto.GenreDto;
import ru.otus.hw.models.Book;

import java.util.List;

@RequiredArgsConstructor
@Component
public class BookConverter {

    private final AuthorConverter authorConverter;

    private final GenreConverter genreConverter;

    public BookDto toDto(Book entity) {
        AuthorDto authorDto = authorConverter.toDto(entity.getAuthor());
        List<GenreDto> genreDtoList = entity.getGenres().stream().map(genreConverter::toDto).toList();
        BookDto dto = new BookDto();
        dto.setId(entity.getId());
        dto.setTitle(entity.getTitle());
        dto.setAuthor(authorDto);
        dto.setGenres(genreDtoList);
        return  dto;
    }
}
