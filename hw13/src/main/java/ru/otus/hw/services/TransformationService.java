package ru.otus.hw.services;

import org.springframework.stereotype.Service;
import ru.otus.hw.models.primary.Author;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import ru.otus.hw.models.primary.Genre;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class TransformationService {

    private final Cache<String, UUID> authorsCache = CacheBuilder.newBuilder()
            .expireAfterWrite(5, TimeUnit.MINUTES)
            .build();

    private final Cache<String, UUID> genresCache = CacheBuilder.newBuilder()
            .expireAfterWrite(5, TimeUnit.MINUTES)
            .build();

    public ru.otus.hw.models.secondary.Author transformationAuthor(Author author) {

        final String authorFullName = author.getFullName();
        final UUID authorUUID = UUID.randomUUID();

        var transformedAuthor = new ru.otus.hw.models.secondary.Author();
        transformedAuthor.setId(authorUUID.toString());
        transformedAuthor.setName(authorFullName);

        authorsCache.put(authorFullName, authorUUID);

        return transformedAuthor;
    }

    public ru.otus.hw.models.secondary.Genre transformationGenre(Genre genre) {

        final String genreName = genre.getName();
        final UUID genreUUID = UUID.randomUUID();

        var transformedGenre = new ru.otus.hw.models.secondary.Genre();
        transformedGenre.setId(genreUUID.toString());
        transformedGenre.setName(genreName);

        genresCache.put(genreName, genreUUID);

        return transformedGenre;
    }
}
