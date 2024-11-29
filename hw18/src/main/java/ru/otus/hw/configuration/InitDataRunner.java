package ru.otus.hw.configuration;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import reactor.core.scheduler.Scheduler;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Genre;
import ru.otus.hw.repositories.AuthorRepository;
import ru.otus.hw.repositories.GenreRepository;

import java.util.Arrays;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class InitDataRunner implements ApplicationRunner {

    private final AuthorRepository authorRepository;

    private final GenreRepository genreRepository;

    private final Scheduler workerPool;

    @Override
    public void run(ApplicationArguments args) {
        log.info("-- Start init data --");
        authorRepository.saveAll(List.of(
                        new Author("Aleksander Pushkin")
                )).publishOn(workerPool)
                .subscribe(savedAuthor -> {
                    log.info("Saved author: {}", savedAuthor.getId());
                    genreRepository.saveAll(Arrays.asList(
                                    new Genre("Detective"),
                                    new Genre("Humor")))
                            .publishOn(workerPool)
                            .subscribe(savedNotes -> log.info("saved genre: {}", savedNotes.getId()));
                });

        authorRepository.findAll()
                .publishOn(workerPool)
                .subscribe(authorDto -> log.info("All author: {}", authorDto.getId()));

        genreRepository.findAll()
                .publishOn(workerPool)
                .subscribe(genreDto -> log.info("All genre: {}", genreDto.getId()));

        log.info("-- Finish init data --");
    }
}
