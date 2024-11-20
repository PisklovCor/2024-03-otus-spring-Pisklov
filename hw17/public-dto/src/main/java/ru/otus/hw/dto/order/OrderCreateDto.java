package ru.otus.hw.dto.order;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "Сущность книги (создание)")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderCreateDto {

    /**
     * User login.
     */
    @Schema(description = "Логин пользователя", example = "guest")
    @NotBlank
    private String login;

    /**
     * Book title from order.
     */
    @Schema(description = "Заголовок книги", example = "Гордость и предубеждение")
    @NotBlank
    private String bookTitle;

}
