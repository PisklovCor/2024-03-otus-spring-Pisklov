package ru.otus.hw.clients;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import ru.otus.hw.dto.account.AccountDto;
import ru.otus.hw.exceptions.ExternalSystemException;

import static ru.otus.hw.dictionaries.ExternalSystem.ACCOUNT_SERVICE;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountClient {

    private static final String ACCOUNT_BY_LOGIN = "account-service/api/v1/account/";

    private final RestClient.Builder accountRestClientBuilder;

    @CircuitBreaker(name = "circuitBreakerAccountRestClient", fallbackMethod = "recoverMethod")
    public AccountDto getAccountByLogin(String login) {

        return accountRestClientBuilder.build().get()
                .uri(ACCOUNT_BY_LOGIN + login)
                .retrieve()
                .onStatus(status -> status.value() == 500, (request, response) -> {
                    throw new ExternalSystemException("Error take a book", ACCOUNT_SERVICE);
                })
                .body(AccountDto.class);
    }

    private AccountDto recoverMethod(String login, Throwable throwable) {
        log.error("Worked circuitBreakerAccountRestClient, login: {} throwable: {}", login, throwable.getMessage());
        throw new ExternalSystemException("Error interacting with the service", ACCOUNT_SERVICE);
    }
}
