package ru.otus.hw.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw.converters.GenreConverter;
import ru.otus.hw.repositories.GenreRepository;
import ru.otus.hw.repositories.GenreRepositoryJpa;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Сервис для работы с жанрами ")
@DataJpaTest
@Import({GenreConverter.class, GenreRepositoryJpa.class})
@Transactional(propagation = Propagation.NEVER)
class GenreServiceImplTest {

    private static final int EXPECTED_NUMBER_OF_GENRE = 6;

    @Autowired
    private GenreConverter genreConverter;

    @Autowired
    private GenreRepository genreRepository;

    private GenreServiceImpl service;

    @BeforeEach
    void setUp() {
        service = new GenreServiceImpl(genreConverter, genreRepository);
    }

    @DisplayName("должен загружать список всех жанров")
    @Test
    void findAll() {
        var genre = service.findAll();
        assertThat(genre).isNotNull().hasSize(EXPECTED_NUMBER_OF_GENRE)
                .allMatch(g -> !g.getName().isEmpty());
    }
}