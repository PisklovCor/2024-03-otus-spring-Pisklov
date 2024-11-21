package ru.otus.hw.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.otus.hw.clients.LibraryClient;
import ru.otus.hw.exceptions.CacheException;
import ru.otus.hw.utils.CacheService;

@Slf4j
@RequiredArgsConstructor
@Service
public class CacheScheduledTasksImpl implements ScheduledTasks {

    private final LibraryClient client;

    @Scheduled(fixedDelayString = "PT010S")
    @Override
    public void run() {
        log.info("Start CacheScheduledTasksImpl");
        fillingCache();
        log.info("Completion CacheScheduledTasksImpl");

    }

    private void fillingCache() {

        try {
            val autorList = client.getListAuthor();
            CacheService.fillingAuthorsCache(autorList);

            val genreList = client.getListGenre();
            CacheService.fillingGenresCache(genreList);

        } catch (Exception e) {
            log.error("Exception CacheScheduledTasksImpl, e=[{}]", e.getMessage());
            throw new CacheException("Cache update error");
        }
    }
}
