package ru.otus.hw.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Schema(description = "Сущность книги (обновление)")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookUpdateDto {

    /**
     * Identifier book.
     */
    @Schema(description = "ID", example = "1")
    @NotNull
    private long id;

    /**
     * Title book.
     */
    @Schema(description = "Заголовок", example = "Джентльмены удачи")
    @NotBlank
    private String title;

    /**
     * Identifier author of the book.
     */
    @Schema(description = "ID автора")
    @NotNull
    private long authorId;

    /**
     * Identifier book genres.
     */
    @Schema(description = "IDs жанров")
    @NotNull
    private List<Long> genresId;

}
