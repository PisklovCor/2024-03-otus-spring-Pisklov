package ru.otus.hw.repositories;

import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.hw.models.Genre;

import java.util.Objects;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий на основе Jpa для работы с жанрами ")
@DataJpaTest
@Import(JpaGenreRepository.class)
class JpaGenreRepositoryTest {

    private static final int EXPECTED_NUMBER_OF_GENRE = 6;
    private static final long FIRST_GENRE_ID = 3L;

    @Autowired
    private GenreRepository repositoryJpa;

    @Autowired
    private TestEntityManager em;

    @DisplayName("должен загружать список всех жанров")
    @Test
    void findAll() {

        val actualGenre = repositoryJpa.findAll();

        assertThat(actualGenre).isNotNull().hasSize(EXPECTED_NUMBER_OF_GENRE)
                .allMatch(g -> !g.getName().isEmpty());
    }

    @DisplayName("должен загружать информацию о нужных жанрах по списку id")
    @Test
    void findAllByIds() {

        val actualGenre = repositoryJpa.findAllByIds(Set.of(FIRST_GENRE_ID));
        val expectedGenre = em.find(Genre.class, FIRST_GENRE_ID);

        assertThat(actualGenre).isNotNull().hasSize(1)
                .allMatch(g -> !g.getName().isEmpty())
                .allMatch(g -> Objects.equals(g, expectedGenre));
    }
}
