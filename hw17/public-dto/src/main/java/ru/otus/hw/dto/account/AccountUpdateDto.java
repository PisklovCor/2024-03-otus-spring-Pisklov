package ru.otus.hw.dto.account;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "Сущность аккаунта пользоваетля (обновление)")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountUpdateDto {

    /**
     * Identifier account.
     */
    @Schema(description = "ID", example = "1")
    @Min(1)
    private long id;

    /**
     * User name.
     */
    @Schema(description = "Имя пользователя", example = "Иван")
    @NotBlank
    private String name;

    /**
     * User surname.
     */
    @Schema(description = "Фамилия пользователя", example = "Иванов")
    @NotBlank
    private String surname;

    /**
     * User login.
     */
    @Schema(description = "Логин пользователя", example = "guest")
    @NotBlank
    private String login;

    /**
     * User mail.
     */
    @Schema(description = "Почтовый адрес пользователя", example = "user@protonmail.com")
    @Email(message = "Please provide a valid email address")
    private String mail;

}
