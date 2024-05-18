package ru.otus.hw.services;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw.converters.AuthorConverter;
import ru.otus.hw.repositories.AuthorRepositoryJpa;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Сервис для работы с авторами ")
@DataJpaTest
@Import({AuthorConverter.class, AuthorRepositoryJpa.class, AuthorServiceImpl.class})
@Transactional(propagation = Propagation.NEVER)
class AuthorServiceImplTest {

    private static final int EXPECTED_NUMBER_OF_AUTHOR = 3;

    @Autowired
    private AuthorService service;

    @DisplayName("должен загружать список всех авторов")
    @Test
    void findAll() {

        var listAuthorDto = service.findAll();

        assertThat(listAuthorDto).isNotNull().hasSize(EXPECTED_NUMBER_OF_AUTHOR)
                .allMatch(a -> !a.getFullName().isEmpty());
    }
}