package ru.otus.hw.services;

import ru.otus.hw.dto.notification.RawMessageCreateDto;
import ru.otus.hw.dto.notification.RawMessageDto;
import java.util.List;

public interface RawMessageService {

    List<RawMessageDto> findAll();

    RawMessageDto create(RawMessageCreateDto dto);
}
