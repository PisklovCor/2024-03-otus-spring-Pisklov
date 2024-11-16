package ru.otus.hw.dto.account;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "Сущность аккаунта пользоваетля")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountDto {

    /**
     * Identifier account.
     */
    @Schema(description = "ID", example = "1")
    private long id;

    /**
     * User name.
     */
    @Schema(description = "Имя пользователя", example = "Иван")
    private String name;

    /**
     * User surname.
     */
    @Schema(description = "Фамилия пользователя", example = "Иванов")
    private String surname;

    /**
     * User login.
     */
    @Schema(description = "Логин пользователя", example = "guest")
    private String login;

    /**
     * User mail.
     */
    @Schema(description = "Почтовый адрес пользователя", example = "user@protonmail.com")
    private String mail;

}