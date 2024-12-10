package ru.otus.hw.jms;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.otus.hw.dictionaries.MessageType;

@Schema(description = "JMS сообщение сервиса библиотеки")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JmsLibraryMessage {

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
     * User comment.
     */
    @Schema(description = "Комментарий пользователя", example = "Позновательный комментарий")
    @NotBlank
    private String comment;

}
