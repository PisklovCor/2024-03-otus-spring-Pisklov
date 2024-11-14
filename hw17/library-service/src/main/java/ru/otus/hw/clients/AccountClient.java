package ru.otus.hw.clients;

import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import ru.otus.hw.dto.account.AccountBookCreateDto;
import ru.otus.hw.exceptions.ExternalSystemException;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static ru.otus.hw.dictionaries.ExternalSystem.ACCOUNT_SERVICE;

@Slf4j
@RequiredArgsConstructor
@Service
public class AccountClient {

    private static final String TAKE_BOOK = "/api/v1/account/book";

    private final RestClient accountRestClient;

    @RateLimiter(name = "rateLimiter")
    public void takeBook(AccountBookCreateDto dto) {

        accountRestClient.post()
                .uri(TAKE_BOOK)
                .contentType(APPLICATION_JSON)
                .body(dto)
                .retrieve()
                .onStatus(status -> status.value() == 500, (request, response) -> {
                    throw new ExternalSystemException("Error take a book", ACCOUNT_SERVICE);
                })
                .toBodilessEntity();
    }
}
