package ru.otus.hw.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw.dto.notification.RawMessageCreateDto;
import ru.otus.hw.dto.notification.RawMessageDto;
import ru.otus.hw.mappers.RawMessageMapper;
import ru.otus.hw.models.RawMessage;
import ru.otus.hw.repositories.RawMessageRepository;
import java.util.List;

import static ru.otus.hw.dictionaries.Status.CREATED;

@Service
@RequiredArgsConstructor
public class RawMessageServiceImpl implements RawMessageService {

    private final RawMessageRepository repository;

    private final RawMessageMapper mapper;

    @Override
    @Transactional(readOnly = true)
    public List<RawMessageDto> findAll() {
        return repository.findAll().stream().map(mapper::toDto).toList();
    }

    @Override
    @Transactional
    public RawMessageDto create(RawMessageCreateDto dto) {
        return mapper.toDto(repository.save(
                RawMessage.builder()
                        .login(dto.getLogin())
                        .content(dto.getContent())
                        .externalSystemName(dto.getExternalSystemName())
                        .messageType(dto.getMessageType())
                        .status(CREATED)
                        .build()));
    }
}
