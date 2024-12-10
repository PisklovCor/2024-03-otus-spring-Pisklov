package ru.otus.hw.mappers;

import org.springframework.stereotype.Component;
import ru.otus.hw.dto.order.OrderDto;
import ru.otus.hw.dto.order.OrderUpdateDto;
import ru.otus.hw.models.Order;

@Component
public class OrderMapper {

    public OrderDto toDto(Order entity) {
        OrderDto dto = new OrderDto();
        dto.setId(entity.getId());
        dto.setLogin(entity.getLogin());
        dto.setBookTitle(entity.getBookTitle());
        dto.setStatus(entity.getStatus());
        return  dto;
    }

    public OrderUpdateDto toUpdateDto(OrderDto dto) {
        OrderUpdateDto orderUpdateDto = new OrderUpdateDto();
        orderUpdateDto.setId(dto.getId());
        orderUpdateDto.setLogin(dto.getLogin());
        orderUpdateDto.setBookTitle(dto.getBookTitle());
        orderUpdateDto.setStatus(dto.getStatus());
        return  orderUpdateDto;
    }
}
