package ru.otus.hw.services;

import ru.otus.hw.dictionaries.Status;
import ru.otus.hw.dto.order.OrderCreateDto;
import ru.otus.hw.dto.order.OrderDto;
import ru.otus.hw.dto.order.OrderUpdateDto;

import java.util.List;

public interface OrderService {

    List<OrderDto> findAll();

    List<OrderDto> findAllByLogin(String login);

    List<OrderDto> findAllByStatus(Status status);

    OrderDto create(OrderCreateDto dto);

    OrderDto update(OrderUpdateDto dto);

}
