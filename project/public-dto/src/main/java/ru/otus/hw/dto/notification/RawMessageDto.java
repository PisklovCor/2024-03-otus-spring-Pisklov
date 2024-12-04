package ru.otus.hw.dto.notification;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import ru.otus.hw.dictionaries.ExternalSystem;
import ru.otus.hw.dictionaries.MessageType;
import ru.otus.hw.dictionaries.Status;

import java.time.LocalDateTime;

@Schema(description = "Сущность сырого сообщения")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RawMessageDto {

    /**
     * Identifier raw message.
     */
    @Schema(description = "ID", example = "1")
    private long id;

    /**
     * Time of creation.
     */
    @Schema(description = "Время создания")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime createdDate;

    /**
     * Last update time.
     */
    @Schema(description = "Время обновления")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime lastModifiedDate;

    /**
     * User login.
     */
    @Schema(description = "Логин пользователя", example = "guest")
    private String login;

    /**
     * Content raw message.
     */
    @Schema(description = "Содержание сообщения", example = "message content")
    private String content;

    /**
     * External system name.
     */
    @Schema(description = "Название внешней системы")
    private ExternalSystem externalSystemName;

    /**
     * Type raw message.
     */
    @Schema(description = "Тип сообщения")
    private MessageType messageType;

    /**
     * Status raw message.
     */
    @Schema(description = "Статус сообщения")
    private Status status;

}
