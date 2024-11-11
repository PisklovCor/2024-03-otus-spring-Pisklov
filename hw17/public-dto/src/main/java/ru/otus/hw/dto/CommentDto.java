package ru.otus.hw.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "Сущность комментария")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto {

    /**
     * Identifier comment.
     */
    @Schema(description = "ID", example = "1")
    private long id;

    /**
     * Content comment.
     */
    @Schema(description = "Содержание", example = "Позновательный комментарий")
    private String content;

}
