package ru.otus.hw.dto.library;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "Сущность жанра")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GenreDto {

    /**
     * Identifier genre.
     */
    @Schema(description = "ID", example = "1")
    private long id;


    /**
     * Name genre.
     */
    @Schema(description = "Название", example = "Ужасы")
    private String name;

}
