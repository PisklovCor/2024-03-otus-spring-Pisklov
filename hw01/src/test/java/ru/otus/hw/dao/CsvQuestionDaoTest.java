package ru.otus.hw.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.Spy;
import ru.otus.hw.config.AppProperties;
import ru.otus.hw.config.TestFileNameProvider;
import ru.otus.hw.exceptions.QuestionReadException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

class CsvQuestionDaoTest {

    private TestFileNameProvider appProperties;

    @Spy
    private QuestionDao csvQuestionDao;

    @BeforeEach
    void setUp() {
        appProperties = Mockito.mock(AppProperties.class);
        csvQuestionDao =  new CsvQuestionDao(appProperties);
    }

    @DisplayName("ожидаемая ошибка чтения файла CSV")
    @Test
    void findAll_throw_QuestionReadException() {

        when(appProperties.getTestFileName()).thenReturn("test");

        assertThrows(QuestionReadException.class, () -> {
            csvQuestionDao.findAll();
        });
    }
}