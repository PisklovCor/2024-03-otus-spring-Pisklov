package ru.otus.hw.services;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import ru.otus.hw.AbstractRepositoryTest;
import ru.otus.hw.converters.AuthorConverter;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Сервис для работы с авторами ")
@Import({AuthorConverter.class,  AuthorServiceImpl.class})
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
class AuthorServiceImplTest extends AbstractRepositoryTest {

    private static final int EXPECTED_NUMBER_OF_AUTHOR = 3;
    private static final List<String> AUTHORS_FULL_NAME = List.of(
            "Erich Maria Remarque", "Fyodor Dostoyevsky", "Ernest Miller Hemingway");

    @Autowired
    private AuthorService service;

    @DisplayName("должен загружать список всех авторов")
    @Test
    void findAll() {

        var listAuthorDto = service.findAll();

        assertThat(listAuthorDto).isNotNull().hasSize(EXPECTED_NUMBER_OF_AUTHOR)
                .allMatch(a -> !a.getFullName().isEmpty())
                .allMatch(a -> AUTHORS_FULL_NAME.contains(a.getFullName()));
    }
}