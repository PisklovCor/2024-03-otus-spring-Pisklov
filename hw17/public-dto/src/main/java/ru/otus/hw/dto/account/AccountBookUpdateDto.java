package ru.otus.hw.dto.account;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "Сущность связи пользователя с книгой (обновление)")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountBookUpdateDto {

    /**
     * Identifier account.
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
     * Identifier book by library-service.
     */
    @Schema(description = "ID книги", example = "1")
    @Min(1)
    private long bookId;

}