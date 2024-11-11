package ru.otus.hw.clients;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import ru.otus.hw.dto.order.OrderCreateDto;
import ru.otus.hw.dto.order.OrderDto;
import ru.otus.hw.exceptions.ExternalSystemException;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@RequiredArgsConstructor
@Service
public class OrderClient {

    private static final String ORDER_CREATE = "/api/v1/order";

    private final RestClient orderRestClient;

    public OrderDto createOrder(OrderCreateDto dto) {
        return orderRestClient.post()
                .uri(ORDER_CREATE)
                .contentType(APPLICATION_JSON)
                .body(dto)
                .retrieve()
                .onStatus(status -> status.value() == 500, (request, response) -> {
                    throw new ExternalSystemException(response.toString());
                })
                .body(OrderDto.class);
    }
}
