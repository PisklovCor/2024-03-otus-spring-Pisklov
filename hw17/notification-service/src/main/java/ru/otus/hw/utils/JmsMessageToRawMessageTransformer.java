package ru.otus.hw.utils;

import lombok.experimental.UtilityClass;
import ru.otus.hw.dictionaries.ExternalSystem;
import ru.otus.hw.dto.notification.RawMessageCreateDto;
import ru.otus.hw.jms.JmsAccountMessage;
import ru.otus.hw.jms.JmsLibraryMessage;
import ru.otus.hw.jms.JmsOrderMessage;

@UtilityClass
public class JmsMessageToRawMessageTransformer {

    public RawMessageCreateDto transformJmsOrderMessage(JmsOrderMessage jms, ExternalSystem system) {

        return RawMessageCreateDto.builder()
                .login(jms.getLogin())
                .content(jms.getBookTitle())
                .externalSystemName(system)
                .messageType(jms.getMessageType())
                .build();
    }

    public RawMessageCreateDto transformJmsAccountMessage(JmsAccountMessage jms, ExternalSystem system) {

        return RawMessageCreateDto.builder()
                .login(jms.getLogin())
                .content(jms.getBookDescription())
                .externalSystemName(system)
                .messageType(jms.getMessageType())
                .build();
    }

    public RawMessageCreateDto transformJJmsLibraryMessage(JmsLibraryMessage jms, ExternalSystem system) {

        return RawMessageCreateDto.builder()
                .login(jms.getLogin())
                .content(jms.getComment())
                .externalSystemName(system)
                .messageType(jms.getMessageType())
                .build();
    }
}
