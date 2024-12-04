package ru.otus.hw.services;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw.SpringBootApplicationTest;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hibernate.internal.util.collections.CollectionHelper.setOf;

@DisplayName("Сервис для работы с авторами ")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
@Transactional(propagation = Propagation.NEVER)
class AuthorServiceImplTest extends SpringBootApplicationTest {

    private static final int EXPECTED_NUMBER_OF_AUTHOR = 3;
    private static final Set<String> AUTHORS_FULL_NAME = setOf("Author_1", "Author_2", "Author_3");
    private static final Set<Long> AUTHORS_IDS = setOf(1L, 2L, 3L);

    @Autowired
    private AuthorService service;

    @DisplayName("должен загружать список всех авторов")
    @Test
    void findAll() {

        var listAuthorDto = service.findAll();

        assertThat(listAuthorDto).isNotNull().hasSize(EXPECTED_NUMBER_OF_AUTHOR)
                .allMatch(a -> !a.getFullName().isEmpty())
                .allMatch(a -> AUTHORS_IDS.contains(a.getId()))
                .allMatch(a -> AUTHORS_FULL_NAME.contains(a.getFullName()));
    }
}