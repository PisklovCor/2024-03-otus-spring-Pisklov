package ru.otus.hw.dto.library;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "Сущность автора")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthorDto {

    /**
     * Identifier author.
     */
    @Schema(description = "ID", example = "1")
    private long id;

    /**
     * Full name author.
     */
    @Schema(description = "Полное имя", example = "Иванов Иван Иванович")
    private String fullName;

}
