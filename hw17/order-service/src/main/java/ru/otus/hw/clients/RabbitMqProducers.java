package ru.otus.hw.clients;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import ru.otus.hw.jms.JmsOrderMessage;

import static ru.otus.hw.configuration.RabbitMqConfiguration.BASE_ROUTING_KEY;
import static ru.otus.hw.dictionaries.MessageType.CREATION;
import static ru.otus.hw.dictionaries.MessageType.UPDATE;

@Slf4j
@Service
@RequiredArgsConstructor
public class RabbitMqProducers {

    private static final String CREATED_QUEUE = "created";

    private static final String CONFIRMED_QUEUE = "confirmed";

    private final RabbitTemplate rabbitTemplate;

    public void sendingCreationMessage(JmsOrderMessage message) {
        message.setMessageType(CREATION);
        sandingMessages(message, CREATED_QUEUE);
    }

    public void sendingUpdateMessage(JmsOrderMessage message) {
        message.setMessageType(UPDATE);
        sandingMessages(message, CONFIRMED_QUEUE);
    }

    private void sandingMessages(JmsOrderMessage message, String queue) {

        val routingKey = BASE_ROUTING_KEY + queue;
        rabbitTemplate.convertAndSend(routingKey, message);
        log.info("Send queue: [{}] message: [{}]", routingKey, message);

    }
}
