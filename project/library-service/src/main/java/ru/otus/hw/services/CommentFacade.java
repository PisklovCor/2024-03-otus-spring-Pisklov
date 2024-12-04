package ru.otus.hw.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.otus.hw.dto.library.CommentCreateDto;
import ru.otus.hw.dto.library.CommentDto;
import ru.otus.hw.dto.library.CommentUpdateDto;
import ru.otus.hw.jms.JmsLibraryMessage;
import ru.otus.hw.producers.RabbitMqProducers;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentFacade {

    private final CommentService service;

    private final RabbitMqProducers producers;

    public CommentDto create(CommentCreateDto dto) {

        var commentDto = service.create(dto);

        try {
            producers.sendingCreationMessage(makeJmsNotificationMessage(commentDto));
        } catch (Exception e) {
            log.error("Failed to send message [{}]", e.getMessage());
            producers.sendingErrorMessage(JmsLibraryMessage.builder()
                    .login(commentDto.getLogin())
                    .build());
        }

        return commentDto;
    }

    public CommentDto update(CommentUpdateDto dto) {

        var commentDto = service.update(dto);

        try {

            producers.sendingUpdateMessage(makeJmsNotificationMessage(commentDto));

        } catch (Exception e) {
            log.error("Failed to send message [{}]", e.getMessage());
            producers.sendingErrorMessage(JmsLibraryMessage.builder()
                    .login(commentDto.getLogin())
                    .build());
        }

        return commentDto;
    }

    private JmsLibraryMessage makeJmsNotificationMessage(CommentDto dto) {
        return JmsLibraryMessage.builder()
                .login(dto.getLogin())
                .comment(dto.getContent())
                .build();
    }
}
