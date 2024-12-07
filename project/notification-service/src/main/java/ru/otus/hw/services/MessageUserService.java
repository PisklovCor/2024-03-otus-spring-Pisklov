package ru.otus.hw.services;

import ru.otus.hw.dictionaries.Status;
import ru.otus.hw.dto.notification.MessageUserDto;
import java.util.List;

public interface MessageUserService {

    List<MessageUserDto> findAll();

    List<MessageUserDto> findAllByLogin(String login);

    List<MessageUserDto> findAllByStatus(Status status);
}
