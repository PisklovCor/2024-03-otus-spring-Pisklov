package ru.otus.hw.services;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import ru.otus.hw.domain.Instrument;
import ru.otus.hw.domain.RawMessage;

import java.util.Collection;

@MessagingGateway
public interface InstrumentGateway {

    @Gateway(requestChannel = "registrationChannel", replyChannel = "tradeChannel")
    Collection<Instrument> process(Collection<RawMessage> rawMessages);
}
