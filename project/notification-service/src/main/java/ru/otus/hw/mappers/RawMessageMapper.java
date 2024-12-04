package ru.otus.hw.mappers;

import org.springframework.stereotype.Component;
import ru.otus.hw.dto.notification.RawMessageDto;
import ru.otus.hw.models.RawMessage;

@Component
public class RawMessageMapper {

    public RawMessageDto toDto(RawMessage entity) {
        return RawMessageDto.builder()
                .id(entity.getId())
                .createdDate(entity.getCreatedDate())
                .lastModifiedDate(entity.getLastModifiedDate())
                .login(entity.getLogin())
                .content(entity.getContent())
                .externalSystemName(entity.getExternalSystemName())
                .messageType(entity.getMessageType())
                .status(entity.getStatus())
                .build();
    }
}
