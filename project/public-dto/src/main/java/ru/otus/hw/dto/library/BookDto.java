package ru.otus.hw.dto.library;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Schema(description = "Сущность книги")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookDto {

    /**
     * Identifier book.
     */
    @Schema(description = "ID", example = "1")
    private long id;

    /**
     * Title book.
     */
    @Schema(description = "Заголовок", example = "Гордость и предубеждение")
    private String title;

    /**
     * Author of the book.
     */
    @Schema(description = "Автор")
    private AuthorDto author;

    /**
     * Book genres.
     */
    @Schema(description = "Жанры")
    private List<GenreDto> genres;

}
