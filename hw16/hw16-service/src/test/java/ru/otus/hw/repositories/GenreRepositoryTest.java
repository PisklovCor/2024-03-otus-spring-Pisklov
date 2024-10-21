package ru.otus.hw.repositories;

import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw.SpringBootApplicationTest;
import ru.otus.hw.models.Genre;

import java.util.Objects;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hibernate.internal.util.collections.CollectionHelper.setOf;

@Deprecated
@DisplayName("Репозиторий на основе Spring Data JPA для работы с жанрами ")
@Transactional
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
class GenreRepositoryTest extends SpringBootApplicationTest {

    private static final int EXPECTED_NUMBER_OF_GENRE = 6;
    private static final long FIRST_GENRE_ID = 1L;
    private static final Set<String> GENRE_FULL_NAME_VALUE =
            setOf("Genre_1", "Genre_2", "Genre_3", "Genre_4", "Genre_5", "Genre_6");

    @Autowired
    private GenreRepository repo;

    @Autowired
    private TestEntityManager em;

    @DisplayName("должен загружать список всех жанров")
    @Test
    void findAll() {

        val actualGenre = repo.findAll();

        assertThat(actualGenre).isNotNull().hasSize(EXPECTED_NUMBER_OF_GENRE)
                .allMatch(g -> !g.getName().isEmpty())
                .allMatch(g -> GENRE_FULL_NAME_VALUE.contains(g.getName()));
    }

    @DisplayName("должен загружать информацию о нужных жанрах по списку id")
    @Test
    void findAllByIds() {

        val actualGenre = repo.findAllById(Set.of(FIRST_GENRE_ID));
        val expectedGenre = em.find(Genre.class, FIRST_GENRE_ID);

        assertThat(actualGenre).isNotNull().hasSize(1)
                .allMatch(g -> !g.getName().isEmpty())
                .allMatch(g -> Objects.equals(g, expectedGenre));
    }
}
