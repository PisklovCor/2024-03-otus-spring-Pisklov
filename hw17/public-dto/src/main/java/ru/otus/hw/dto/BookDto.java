package ru.otus.hw.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookDto {

    /**
     * Identifier book.
     */
    private long id;

    /**
     * Title book.
     */
    private String title;

    /**
     * Author of the book.
     */
    private AuthorDto author;

    /**
     * Book genres.
     */
    private List<GenreDto> genres;
}
