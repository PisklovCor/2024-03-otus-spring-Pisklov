package ru.otus.hw.clients;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import ru.otus.hw.dto.order.OrderDto;
import ru.otus.hw.exceptions.ExternalSystemException;

import java.util.Collections;
import java.util.List;

import static ru.otus.hw.dictionaries.ExternalSystem.ORDER_SERVICE;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderClient {

    private static final String ORDER_BY_LOGIN = "order-service/api/v1/order/{login}";

    private final RestClient.Builder orderRestClientBuilder;

    @CircuitBreaker(name = "circuitBreakerOrderRestClient", fallbackMethod = "recoverMethod")
    public List<OrderDto> getOrderByLogin(String login) {

        return orderRestClientBuilder
                .build().get().uri(ORDER_BY_LOGIN, login)
                .retrieve()
                .onStatus(status -> status.value() == 500, (request, response) -> {
                    throw new ExternalSystemException("Error creating order", ORDER_SERVICE);
                })
                .body(new ParameterizedTypeReference<>() {
                });
    }

    private List<OrderDto> recoverMethod(String login, Throwable throwable) {
        log.error("Worked circuitBreakerOrderRestClient, login: {} throwable: {}", login, throwable.getMessage());
        return Collections.emptyList();
    }
}
