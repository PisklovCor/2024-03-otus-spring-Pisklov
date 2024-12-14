package ru.otus.hw.mappers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.hw.dto.AuthorDto;
import ru.otus.hw.dto.BookCreateDto;
import ru.otus.hw.dto.BookDto;
import ru.otus.hw.dto.BookUpdateDto;
import ru.otus.hw.dto.GenreDto;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Genre;

import java.util.List;

@Component
@RequiredArgsConstructor
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

    public Book toEntity(BookCreateDto dto, Author a, List<Genre> g) {
        Book entity = new Book();
        entity.setTitle(dto.getTitle());
        entity.setAuthor(a);
        entity.setGenres(g);
        return  entity;
    }

    public Book toEntity(BookUpdateDto dto, Author a, List<Genre> g) {
        Book entity = new Book();
        entity.setId(dto.getId());
        entity.setTitle(dto.getTitle());
        entity.setAuthor(a);
        entity.setGenres(g);
        return  entity;
    }
}
