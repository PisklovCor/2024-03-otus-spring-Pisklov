package ru.otus.hw.dto.order;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.otus.hw.dictionaries.Status;

@Schema(description = "Сущность заказа (обновление)")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderUpdateDto {

    /**
     * Identifier order.
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
     * Book title from order.
     */
    @Schema(description = "Заголовок книги", example = "Джентльмены удачи")
    @NotBlank
    private String bookTitle;

    /**
     * Order status.
     */
    @Schema(description = "Статус", example = "CONFIRMED")
    @NotNull
    private Status status;

}
