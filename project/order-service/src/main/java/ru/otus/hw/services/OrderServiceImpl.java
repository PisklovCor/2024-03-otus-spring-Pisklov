package ru.otus.hw.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw.dictionaries.Status;
import ru.otus.hw.dto.order.OrderCreateDto;
import ru.otus.hw.dto.order.OrderDto;
import ru.otus.hw.dto.order.OrderUpdateDto;
import ru.otus.hw.exceptions.NotFoundException;
import ru.otus.hw.mappers.OrderMapper;
import ru.otus.hw.models.Order;
import ru.otus.hw.repositories.OrderRepository;

import java.time.LocalDateTime;
import java.util.List;

import static ru.otus.hw.dictionaries.Status.CREATED;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderMapper mapper;

    private final OrderRepository repository;

    @Override
    @Transactional(readOnly = true)
    public List<OrderDto> findAll() {
        return repository.findAll().stream().map(mapper::toDto).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderDto> findAllByLogin(String login) {

        return repository.findAllByLogin(login).stream().map(mapper::toDto).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderDto> findAllByStatus(Status status) {

        return repository.findAllByStatus(status).stream().map(mapper::toDto).toList();
    }

    @Override
    @Transactional
    public OrderDto create(OrderCreateDto dto) {
        return mapper.toDto(repository.save(
                new Order(null, LocalDateTime.now(), null,
                        dto.getLogin(), dto.getBookTitle(), CREATED)));
    }

    @Override
    @Transactional
    public OrderDto update(OrderUpdateDto dto) {
        final long orderId = dto.getId();

        var orderEntity = repository.findById(orderId)
                .orElseThrow(() -> new NotFoundException("Order with id %d not found".formatted(orderId)));

        orderEntity.setLastModifiedDate(LocalDateTime.now());
        orderEntity.setLogin(dto.getLogin());
        orderEntity.setBookTitle(dto.getBookTitle());
        orderEntity.setStatus(dto.getStatus());

        return mapper.toDto(repository.save(orderEntity));
    }
}
