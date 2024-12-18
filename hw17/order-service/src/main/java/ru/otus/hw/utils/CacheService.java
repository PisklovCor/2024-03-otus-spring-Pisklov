package ru.otus.hw.utils;

import lombok.experimental.UtilityClass;
import lombok.val;
import ru.otus.hw.dto.library.AuthorDto;
import ru.otus.hw.dto.library.GenreDto;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;

@UtilityClass
public class CacheService {

    private final List<Long> authorsCache = new ArrayList<>();

    private final List<Long> genresCache = new ArrayList<>();

    public Long getRandomElementAuthorsCache() {

        if (authorsCache.isEmpty()) {
            throw new NoSuchElementException("Element cache authorsCache is empty");
        }

        Random rand = new Random();
        return authorsCache.get(rand.nextInt(authorsCache.size()));
    }

    public Long getRandomElementGenresCache() {

        if (genresCache.isEmpty()) {
            throw new NoSuchElementException("Element cache genresCache is empty");
        }

        Random rand = new Random();
        return genresCache.get(rand.nextInt(genresCache.size()));
    }

    public void fillingAuthorsCache(final List<AuthorDto> list) {

        val listId = list.stream().map(AuthorDto::getId).toList();

        for (Long id : listId) {
            if (!authorsCache.contains(id)) {
                authorsCache.add(id);
            }
        }
    }

    public void fillingGenresCache(final List<GenreDto> listGenre) {

        val listId = listGenre.stream().map(GenreDto::getId).toList();

        for (Long id : listId) {
            if (!genresCache.contains(id)) {
                genresCache.add(id);
            }
        }
    }

}
