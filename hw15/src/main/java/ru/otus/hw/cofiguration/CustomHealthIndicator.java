package ru.otus.hw.cofiguration;

import lombok.AllArgsConstructor;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.actuate.health.Status;
import org.springframework.stereotype.Component;
import ru.otus.hw.mappers.BookMapper;
import ru.otus.hw.repositories.BookRepository;

@Component
@AllArgsConstructor
public class CustomHealthIndicator implements HealthIndicator {

    private final BookMapper bookMapper;

    private final BookRepository bookRepository;

    @Override
    public Health health() {

        var bookList = bookRepository.findAll().stream().map(bookMapper::toDto).toList();;

        if (bookList.isEmpty()) {

            return Health.down()
                    .status(Status.DOWN)
                    .withDetail("message", "There are no books in the library")
                    .build();
        } else {

            return Health.up()
                    .withDetail("message", "The library is full")
                    .build();
        }
    }
}
