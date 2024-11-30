package ru.otus.hw.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import ru.otus.hw.exceptions.NotFoundException;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Genre;
import ru.otus.hw.repositories.AuthorRepository;
import ru.otus.hw.repositories.GenreRepository;

import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DataAcquisitionService {

    private final AuthorRepository authorRepository;

    private final GenreRepository genreRepository;

    public Mono<Author> findMonoAuthorById(String authorId) {
        return authorRepository.findById(authorId)
                .switchIfEmpty(Mono.create(emitter -> emitter.error(
                        new NotFoundException("Author with id %s not found".formatted(authorId)))));
    }

    public Mono<List<Genre>> findAllMonoGenreByIds(List<String> genresIds) {

        var genresIdsSet = new HashSet<>(genresIds);

        return genreRepository.findAllById(genresIdsSet)
                .collectList()
                .flatMap(list -> {
                    if (list.size() != genresIdsSet.size()) {
                        return Mono.create(emitter -> emitter.error(
                                new NotFoundException("Not all genres found from list %s"
                                        .formatted(genresIdsSet))));
                    } else {
                        return Mono.just(list);
                    }
                });
    }
}
