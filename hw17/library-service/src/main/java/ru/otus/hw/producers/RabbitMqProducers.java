package ru.otus.hw.producers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import ru.otus.hw.jms.JmsLibraryMessage;

import static ru.otus.hw.dictionaries.MessageType.CREATION;
import static ru.otus.hw.dictionaries.MessageType.ERROR;
import static ru.otus.hw.dictionaries.MessageType.UPDATE;

@Slf4j
@Service
@RequiredArgsConstructor
public class RabbitMqProducers {

    private static final String BASE_ROUTING_KEY = "library.message.";

    private static final String CREATED_ROUTING_KEY = "created";

    private static final String CONFIRMED_ROUTING_KEY = "confirmed";

    private static final String ERROR_ROUTING_KEY = "error";

    private final RabbitTemplate rabbitTemplate;

    public void sendingCreationMessage(JmsLibraryMessage message) {
        message.setMessageType(CREATION);
        sandingMessages(message, CREATED_ROUTING_KEY);
    }

    public void sendingUpdateMessage(JmsLibraryMessage message) {
        message.setMessageType(UPDATE);
        sandingMessages(message, CONFIRMED_ROUTING_KEY);
    }

    public void sendingErrorMessage(JmsLibraryMessage message) {
        message.setMessageType(ERROR);
        sandingMessages(message, ERROR_ROUTING_KEY);
    }

    private void sandingMessages(JmsLibraryMessage message, String queue) {

        val routingKey = BASE_ROUTING_KEY + queue;
        rabbitTemplate.convertAndSend(routingKey, message);
        log.info("Send queue: {} message: {}", routingKey, message);
    }
}
