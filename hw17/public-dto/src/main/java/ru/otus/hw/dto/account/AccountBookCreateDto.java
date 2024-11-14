package ru.otus.hw.dto.account;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "Сущность связи пользователя с книгой (создание)")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountBookCreateDto {

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
    @NotNull
    private long bookId;

}