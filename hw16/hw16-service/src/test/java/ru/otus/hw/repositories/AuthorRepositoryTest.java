package ru.otus.hw.repositories;

import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw.SpringBootApplicationTest;
import ru.otus.hw.models.Author;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hibernate.internal.util.collections.CollectionHelper.setOf;

@Deprecated
@DisplayName("Репозиторий на основе Spring Data JPA для работы с авторами ")
@Transactional
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
class AuthorRepositoryTest extends SpringBootApplicationTest {

    private static final int EXPECTED_NUMBER_OF_AUTHOR = 3;
    private static final long FIRST_AUTHOR_ID = 1L;
    private static final Set<String> AUTHORS_FULL_NAME_VALUE = setOf("Author_1", "Author_2", "Author_3");
    private static final Set<Long> AUTHORS_IDS = setOf(1L, 2L, 3L);


    @Autowired
    private AuthorRepository repo;

    @Autowired
    private TestEntityManager em;

    @DisplayName("должен загружать список всех авторов")
    @Test
    void findAll() {

        val actualAuthor = repo.findAll();

        assertThat(actualAuthor).isNotNull().hasSize(EXPECTED_NUMBER_OF_AUTHOR)
                .allMatch(a -> !a.getFullName().isEmpty())
                .allMatch(a -> AUTHORS_IDS.contains(a.getId()))
                .allMatch(a -> AUTHORS_FULL_NAME_VALUE.contains(a.getFullName()));
    }

    @DisplayName("должен загружать информацию о нужном авторе по его id")
    @Test
    void findById() {

        val actualAuthor = repo.findById(FIRST_AUTHOR_ID);
        val expectedAuthor = em.find(Author.class, FIRST_AUTHOR_ID);

        assertThat(actualAuthor).isPresent().get().isEqualTo(expectedAuthor);
    }
}
