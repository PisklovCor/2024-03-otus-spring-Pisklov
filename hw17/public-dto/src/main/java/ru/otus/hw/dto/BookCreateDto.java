package ru.otus.hw.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Schema(description = "Сущность книги (создание)")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookCreateDto {

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
    @Min(1)
    private long authorId;

    /**
     * Identifier book genres.
     */
    @Schema(description = "IDs жанров")
    @Size(min = 1)
    private List<Long> genresId;

}
