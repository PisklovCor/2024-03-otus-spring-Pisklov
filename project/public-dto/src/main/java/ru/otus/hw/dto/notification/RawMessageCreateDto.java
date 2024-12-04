package ru.otus.hw.dto.notification;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.otus.hw.dictionaries.ExternalSystem;
import ru.otus.hw.dictionaries.MessageType;

@Schema(description = "Сущность сырого сообщения (создание)")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RawMessageCreateDto {

    /**
     * User login.
     */
    @Schema(description = "Логин пользователя", example = "guest")
    @NotBlank
    private String login;

    /**
     * Content raw message.
     */
    @Schema(description = "Содержание сообщения", example = "message content")
    @NotBlank
    private String content;

    /**
     * External system name.
     */
    @Schema(description = "Название внешней системы")
    @NotNull
    private ExternalSystem externalSystemName;

    /**
     * Type raw message.
     */
    @Schema(description = "Тип сообщения")
    @NotNull
    private MessageType messageType;

}
