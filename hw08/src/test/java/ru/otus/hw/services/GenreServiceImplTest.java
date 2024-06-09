package ru.otus.hw.services;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import ru.otus.hw.AbstractRepositoryTest;
import ru.otus.hw.converters.GenreConverter;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Сервис для работы с жанрами ")
@Import({GenreConverter.class, GenreServiceImpl.class})
class GenreServiceImplTest extends AbstractRepositoryTest {

    private static final int EXPECTED_NUMBER_OF_GENRE = 6;
    private static final List<String> GENRE_NAME = List.of(
            "Fiction", "Mystery", "Thriller", "Science fiction", "Fantasy", "Romance");

    @Autowired
    private GenreService service;

    @DisplayName("должен загружать список всех жанров")
    @Test
    void findAll() {
        var listGenreDto = service.findAll();

        assertThat(listGenreDto).isNotNull().hasSize(EXPECTED_NUMBER_OF_GENRE)
                .allMatch(g -> !g.getName().isEmpty())
                .allMatch(g -> GENRE_NAME.contains(g.getName()));
    }
}
