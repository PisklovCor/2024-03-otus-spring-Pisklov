package ru.otus.hw.services;

import ru.otus.hw.domain.Instrument;
import ru.otus.hw.domain.RawMessage;

public interface RegistrationService {

    Instrument registration(RawMessage message);
}
