package ru.otus.hw.dto.account;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.otus.hw.dto.library.BookDto;

@Schema(description = "Сущность связи пользователя с книгой")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountBookDto {

    /**
     * Identifier account.
     */
    @Schema(description = "ID", example = "1")
    private long id;

    /**
     * User login.
     */
    @Schema(description = "Пользователь")
    private AccountDto account;

    /**
     * Book from the library.
     */
    @Schema(description = "Книга")
    private BookDto book;

}