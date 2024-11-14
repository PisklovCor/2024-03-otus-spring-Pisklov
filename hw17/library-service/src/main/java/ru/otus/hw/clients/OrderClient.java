package ru.otus.hw.clients;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import ru.otus.hw.dto.order.OrderCreateDto;
import ru.otus.hw.dto.order.OrderDto;
import ru.otus.hw.exceptions.ExternalSystemException;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static ru.otus.hw.dictionaries.ExternalSystem.ORDER_SERVICE;

@Slf4j
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
                    throw new ExternalSystemException("Error creating order", ORDER_SERVICE);
                })
                .body(OrderDto.class);
    }
}
