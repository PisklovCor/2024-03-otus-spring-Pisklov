package ru.otus.hw.clients;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import ru.otus.hw.jms.JmsAccountMessage;

import static ru.otus.hw.dictionaries.MessageType.CREATION;
import static ru.otus.hw.dictionaries.MessageType.UPDATE;
import static ru.otus.hw.dictionaries.MessageType.ERROR;

@Slf4j
@Service
@RequiredArgsConstructor
public class RabbitMqProducers {

    private static final String BASE_ROUTING_KEY = "account.message.";

    private static final String CREATED_ROUTING_KEY = "created";

    private static final String CONFIRMED_ROUTING_KEY = "confirmed";

    private static final String ERROR_ROUTING_KEY = "error";

    private final RabbitTemplate rabbitTemplate;

    public void sendingCreationMessage(JmsAccountMessage message) {
        message.setMessageType(CREATION);
        sandingMessages(message, CREATED_ROUTING_KEY);
    }

    public void sendingUpdateMessage(JmsAccountMessage message) {
        message.setMessageType(UPDATE);
        sandingMessages(message, CONFIRMED_ROUTING_KEY);
    }

    public void sendingErrorMessage(JmsAccountMessage message) {
        message.setMessageType(ERROR);
        sandingMessages(message, ERROR_ROUTING_KEY);
    }

    private void sandingMessages(JmsAccountMessage message, String queue) {

        val routingKey = BASE_ROUTING_KEY + queue;
        rabbitTemplate.convertAndSend(routingKey, message);
        log.info("Send queue: {} message: {}", routingKey, message);
    }
}
