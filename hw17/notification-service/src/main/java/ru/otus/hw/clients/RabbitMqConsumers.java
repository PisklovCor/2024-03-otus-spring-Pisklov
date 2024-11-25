package ru.otus.hw.clients;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import ru.otus.hw.jms.JmsAccountMessage;
import ru.otus.hw.jms.JmsOrderMessage;
import ru.otus.hw.services.RawMessageService;
import ru.otus.hw.utils.JmsMessageToRawMessageTransformer;

import static ru.otus.hw.dictionaries.ExternalSystem.ACCOUNT_SERVICE;
import static ru.otus.hw.dictionaries.ExternalSystem.ORDER_SERVICE;

@Slf4j
@Service
@RequiredArgsConstructor
public class RabbitMqConsumers {

    private final RawMessageService service;

    @RabbitListener(queues = "order-messages")
    public void processImportantOrderMessages(JmsOrderMessage message) {
        log.info("Received JmsOrderMessage from order-messages: {}", message);

        try {
            val rawMessageDto = service.create(JmsMessageToRawMessageTransformer
                    .transformJmsOrderMessage(message, ORDER_SERVICE));
            log.info("The message has been processed and saved as: {}", rawMessageDto);
        } catch (Exception e) {
            throw new AmqpRejectAndDontRequeueException("An error occurred while processing the message");
        }
    }

    @RabbitListener(queues = "account-messages")
    public void processImportantAccountMessages(JmsAccountMessage message) {
        log.info("Received JmsAccountMessage from account-messages: {}", message);

        try {
            val rawMessageDto = service.create(JmsMessageToRawMessageTransformer
                    .transformJmsAccountMessage(message, ACCOUNT_SERVICE));
            log.info("The message has been processed and saved as: {}", rawMessageDto);
        } catch (Exception e) {
            throw new AmqpRejectAndDontRequeueException("An error occurred while processing the message");
        }
    }
}
