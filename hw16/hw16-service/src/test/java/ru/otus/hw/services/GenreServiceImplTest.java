package ru.otus.hw.services;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw.SpringBootApplicationTest;
import ru.otus.hw.mappers.GenreMapper;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hibernate.internal.util.collections.CollectionHelper.setOf;

@DisplayName("Сервис для работы с жанрами ")
@Import({GenreMapper.class, GenreServiceImpl.class})
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
@Transactional(propagation = Propagation.NEVER)
class GenreServiceImplTest extends SpringBootApplicationTest {

    private static final int EXPECTED_NUMBER_OF_GENRE = 6;
    private static final Set<String> GENRE_FULL_NAME =
            setOf("Genre_1", "Genre_2", "Genre_3", "Genre_4", "Genre_5", "Genre_6");

    @Autowired
    private GenreService service;

    @DisplayName("должен загружать список всех жанров")
    @Test
    void findAll() {
        var listGenreDto = service.findAll();
        assertThat(listGenreDto).isNotNull().hasSize(EXPECTED_NUMBER_OF_GENRE)
                .allMatch(g -> !g.getName().isEmpty())
                .allMatch(g -> GENRE_FULL_NAME.contains(g.getName()));
    }
}
