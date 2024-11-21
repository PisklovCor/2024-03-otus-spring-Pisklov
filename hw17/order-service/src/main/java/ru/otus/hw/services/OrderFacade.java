package ru.otus.hw.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.stereotype.Service;
import ru.otus.hw.clients.RabbitMqProducers;
import ru.otus.hw.dto.order.OrderCreateDto;
import ru.otus.hw.dto.order.OrderDto;
import ru.otus.hw.dto.order.OrderUpdateDto;
import ru.otus.hw.jms.JmsOrderMessage;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderFacade {

    private final OrderService service;

    private final RabbitMqProducers producers;

    public OrderDto createAndSendMessage(OrderCreateDto dto) {

        val newOrderDto = service.create(dto);

        JmsOrderMessage message = new JmsOrderMessage();
        message.setLogin(newOrderDto.getLogin());
        message.setBookTitle(newOrderDto.getBookTitle());

        try {
            producers.sendingCreationMessage(message);
        } catch (Exception e) {
            log.error("Failed to send message [{}]", e.getMessage());
        }

        return newOrderDto;
    }

    public OrderDto updateAndSendMessage(OrderUpdateDto dto) {

        val updateOrderDto = service.update(dto);

        try {
            producers.sendingUpdateMessage(
                    makeJmsOrderMessage(updateOrderDto.getLogin(), updateOrderDto.getBookTitle()));
        } catch (Exception e) {
            log.error("Failed to send message [{}]", e.getMessage());
        }

        return updateOrderDto;
    }

    private JmsOrderMessage makeJmsOrderMessage(String login, String bookTitle) {
        JmsOrderMessage message = new JmsOrderMessage();
        message.setLogin(login);
        message.setBookTitle(bookTitle);
        return message;
    }
}
