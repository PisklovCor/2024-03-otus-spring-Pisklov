package ru.otus.hw.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw.dictionaries.Status;
import ru.otus.hw.dto.notification.MessageUserDto;
import ru.otus.hw.mappers.MessageUserMapper;
import ru.otus.hw.repositories.MessageUserRepository;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageUserServiceImpl implements MessageUserService {

    private final MessageUserRepository repository;

    private final MessageUserMapper mapper;

    @Override
    @Transactional(readOnly = true)
    public List<MessageUserDto> findAll() {
        return repository.findAll().stream().map(mapper::toDto).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<MessageUserDto> findAllByLogin(String login) {
        return repository.findAllByLogin(login).stream().map(mapper::toDto).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<MessageUserDto> findAllByStatus(Status status) {
        return repository.findAllByStatus(status).stream().map(mapper::toDto).toList();
    }
}
