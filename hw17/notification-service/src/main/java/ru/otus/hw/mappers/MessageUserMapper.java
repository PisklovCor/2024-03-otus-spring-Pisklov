package ru.otus.hw.mappers;

import org.springframework.stereotype.Component;
import ru.otus.hw.dto.notification.MessageUserDto;
import ru.otus.hw.models.MessageUser;

@Component
public class MessageUserMapper {

    public MessageUserDto toDto(MessageUser entity) {
        return MessageUserDto.builder()
                .id(entity.getId())
                .createdDate(entity.getCreatedDate())
                .lastModifiedDate(entity.getLastModifiedDate())
                .login(entity.getLogin())
                .mail(entity.getMail())
                .status(entity.getStatus())
                .build();
    }
}
