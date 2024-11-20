package ru.otus.hw.jms;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.otus.hw.dictionaries.MessageType;

@Schema(description = "JMS сообщение сервиса аккаунтов")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JmsNotificationMessage {

    /**
     * User login.
     */
    @Schema(description = "Логин пользователя", example = "guest")
    @NotBlank
    private String login;

    /**
     * Message type.
     */
    @Schema(description = "Тип сообщения")
    @NotBlank
    private MessageType messageType;

    /**
     * Book title from order.
     */
    @Schema(description = "Описание книги", example = "Гордость и предубеждение, Джейн Остен")
    @NotBlank
    private String bookDescription;

}
