package ru.otus.hw.dto.library;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
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
    @Min(1)
    private long id;

    /**
     * Title book.
     */
    @Schema(description = "Заголовок", example = "Гордость и предубеждение")
    @NotBlank
    private String title;

    /**
     * Identifier author of the book.
     */
    @Schema(description = "ID автора", example = "1")
    @Min(1)
    private long authorId;

    /**
     * Identifier book genres.
     */
    @Schema(description = "IDs жанров")
    @Size(min = 1)
    private List<Long> genresId;

}
