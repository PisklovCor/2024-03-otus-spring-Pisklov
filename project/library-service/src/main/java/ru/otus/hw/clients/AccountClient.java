package ru.otus.hw.clients;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import ru.otus.hw.dto.account.AccountBookCreateDto;
import ru.otus.hw.dto.account.AccountBookDto;
import ru.otus.hw.exceptions.ExternalSystemException;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static ru.otus.hw.dictionaries.ExternalSystem.ACCOUNT_SERVICE;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountClient {

    private static final String TAKE_BOOK = "account-service/api/v1/account/book";

    private final RestClient.Builder accountRestClientBuilder;

    @CircuitBreaker(name = "circuitBreakerAccountRestClient", fallbackMethod = "recoverMethod")
    public AccountBookDto takeBook(AccountBookCreateDto dto) {

       return  accountRestClientBuilder.build().post()
                .uri(TAKE_BOOK)
                .contentType(APPLICATION_JSON)
                .body(dto)
                .retrieve()
                .onStatus(status -> status.value() == 500, (request, response) -> {
                    throw new ExternalSystemException("Error take a book", ACCOUNT_SERVICE);
                })
                .body(AccountBookDto.class);
    }

    private void recoverMethod(AccountBookCreateDto dto, Exception ex) {
        log.error("Worked circuitBreakerAccountRestClient, AccountBookCreateDto: {}, e: [{}]", dto, ex.getMessage());
        throw new ExternalSystemException("Error interacting with the service", ACCOUNT_SERVICE);
    }
}
