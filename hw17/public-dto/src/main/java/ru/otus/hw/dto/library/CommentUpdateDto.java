package ru.otus.hw.dto.library;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "Сущность комментария (обновление)")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentUpdateDto {

    /**
     * Identifier comment.
     */
    @Schema(description = "ID", example = "1")
    @Min(1)
    private long id;

    /**
     * User login.
     */
    @Schema(description = "Логин пользователя", example = "guest")
    @NotBlank
    private String login;

    /**
     * Content comment.
     */
    @Schema(description = "Содержание", example = "Позновательный комментарий")
    @NotBlank
    private String content;

    /**
     * Identifier book.
     */
    @Schema(description = "ID книги", example = "1")
    @Min(1)
    private long bookId;

}
