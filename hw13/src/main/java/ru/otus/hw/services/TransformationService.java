package ru.otus.hw.services;

import org.springframework.stereotype.Service;
import ru.otus.hw.exceptions.NoSuchElementCacheException;
import ru.otus.hw.models.primary.Author;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import ru.otus.hw.models.primary.Book;
import ru.otus.hw.models.primary.Genre;

import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

@Service
public class TransformationService {

    private final Cache<String, ru.otus.hw.models.secondary.Author> authorsCache = CacheBuilder.newBuilder()
            .expireAfterWrite(5, TimeUnit.MINUTES)
            .build();

    private final Cache<String, ru.otus.hw.models.secondary.Genre> genresCache = CacheBuilder.newBuilder()
            .expireAfterWrite(5, TimeUnit.MINUTES)
            .build();

    public ru.otus.hw.models.secondary.Author transformationAuthor(Author author) {

        final String authorFullName = author.getFullName();
        final UUID authorUUID = UUID.randomUUID();

        var transformedAuthor = new ru.otus.hw.models.secondary.Author();
        transformedAuthor.setId(authorUUID.toString());
        transformedAuthor.setName(authorFullName);

        authorsCache.put(authorFullName, transformedAuthor);

        return transformedAuthor;
    }

    public ru.otus.hw.models.secondary.Genre transformationGenre(Genre genre) {

        final String genreName = genre.getName();
        final UUID genreUUID = UUID.randomUUID();

        var transformedGenre = new ru.otus.hw.models.secondary.Genre();
        transformedGenre.setId(genreUUID.toString());
        transformedGenre.setName(genreName);
        genresCache.put(genreName, transformedGenre);

        return transformedGenre;
    }

    public ru.otus.hw.models.secondary.Book transformationBook(Book book) throws ExecutionException {

        final UUID bookUUID = UUID.randomUUID();

        var author = authorsCache.get(book.getAuthor().getFullName(),
                new Callable<ru.otus.hw.models.secondary.Author>() {
            @Override
            public ru.otus.hw.models.secondary.Author call()  {
                throw new NoSuchElementCacheException("Element not found in cache");
            }
        });

        var transformedBook = new ru.otus.hw.models.secondary.Book();
        transformedBook.setId(bookUUID.toString());
        transformedBook.setTitle(book.getTitle());
        transformedBook.setAuthor(author);

        return transformedBook;
    }
}
