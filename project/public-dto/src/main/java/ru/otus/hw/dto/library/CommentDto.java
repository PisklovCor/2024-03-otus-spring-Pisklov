package ru.otus.hw.dto.library;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "Сущность комментария")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto {

    /**
     * Identifier comment.
     */
    @Schema(description = "ID", example = "1")
    private long id;

    /**
     * User login.
     */
    @Schema(description = "Логин пользователя", example = "guest")
    private String login;

    /**
     * Content comment.
     */
    @Schema(description = "Содержание", example = "Позновательный комментарий")
    private String content;

}
