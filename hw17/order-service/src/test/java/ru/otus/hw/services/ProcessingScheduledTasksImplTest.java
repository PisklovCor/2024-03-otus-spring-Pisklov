package ru.otus.hw.services;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.testcontainers.shaded.org.awaitility.Durations;
import ru.otus.hw.SpringBootApplicationTest;

import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.verify;
import static org.testcontainers.shaded.org.awaitility.Awaitility.await;


@DisplayName("Сервис периодически запускаемых задач ")
class ProcessingScheduledTasksImplTest extends SpringBootApplicationTest  {

    @SpyBean
    ScheduledTasks tasks;

    @DisplayName("должен найти записи по заказам и обновить их")
    @Test
    public void creationBookBasedOnOrder() {
        await().atMost(Durations.TEN_SECONDS).untilAsserted(() -> {
            verify(tasks, atLeast(2)).run();
        });
    }
}