package ru.otus.hw.clients;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import ru.otus.hw.dto.notification.MessageUserDto;
import ru.otus.hw.exceptions.ExternalSystemException;

import java.util.Collections;
import java.util.List;

import static ru.otus.hw.dictionaries.ExternalSystem.NOTIFICATION_SERVICE;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationClient {

    private static final String MESSAGE_USER_BY_LOGIN = "notification-service/api/v1/user-message/{login}";

    private final RestClient.Builder notificationRestClientBuilder;

    @CircuitBreaker(name = "circuitBreakerNotificationRestClient", fallbackMethod = "recoverMethod")
    public List<MessageUserDto> getAllMessageUserByLogin(String login) {

        return notificationRestClientBuilder
                .build().get().uri(MESSAGE_USER_BY_LOGIN, login)
                .retrieve()
                .onStatus(status -> status.value() == 500, (request, response) -> {
                    throw new ExternalSystemException("Error get message users", NOTIFICATION_SERVICE);
                })
                .body(new ParameterizedTypeReference<>() {
                });
    }

    private List<MessageUserDto> recoverMethod(String login, Throwable throwable) {
        log.error("Worked circuitBreakerNotificationRestClient, login: {} throwable: {}",
                login, throwable.getMessage());
        return Collections.emptyList();
    }
}
