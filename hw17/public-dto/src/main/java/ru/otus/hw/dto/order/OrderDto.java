package ru.otus.hw.dto.order;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.otus.hw.dictionaries.Status;

@Schema(description = "Сущность заказа")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {

    /**
     * Identifier order.
     */
    @Schema(description = "ID", example = "1")
    private long id;

    /**
     * User login.
     */
    @Schema(description = "Логин пользователя", example = "guest")
    private String login;

    /**
     * Book title from order.
     */
    @Schema(description = "Заголовок книги", example = "Гордость и предубеждение")
    private String bookTitle;

    /**
     * Order status.
     */
    @Schema(description = "Статус", example = "CONFIRMED")
    private Status status;

}
