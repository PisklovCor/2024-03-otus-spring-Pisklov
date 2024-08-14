package ru.otus.hw.dao;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.TestPropertySource;
import ru.otus.hw.config.AppProperties;
import ru.otus.hw.domain.Question;

import java.util.List;

@ExtendWith(MockitoExtension.class)
@DisplayName("Сервис для работы с файловой системой ")
@TestPropertySource("classpath:application_test.properties")
class CsvQuestionDaoTest {

    @InjectMocks
    private AppProperties appProperties = new AppProperties("questions_test.csv", 3);

    @DisplayName("должен проверить количество вопросов")
    @Test
    void findAll() {
        CsvQuestionDao csvQuestionDao = new CsvQuestionDao(appProperties);
        final List<Question> questionList = csvQuestionDao.findAll();

        Assertions.assertEquals(5, questionList.size());
    }
}