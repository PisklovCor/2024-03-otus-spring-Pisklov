package ru.otus.hw.dao;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.hw.config.AppProperties;
import ru.otus.hw.domain.Question;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ExtendWith(MockitoExtension.class)
@DisplayName("Сервис для работы с файловой системой ")
class CsvQuestionDaoTest {

    @DisplayName("должен проверить количество вопросов")
    @Test
    void findAll() {
        final Map<String, String> mockFile = new HashMap<>();
        mockFile.put("ru-RU", "questions_test.csv");

        final AppProperties properties = new AppProperties();
        properties.setRightAnswersCountToPass(1);
        properties.setLocale("ru-RU");
        properties.setFileNameByLocaleTag(mockFile);

        final CsvQuestionDao csvQuestionDao = new CsvQuestionDao(properties);
        final List<Question> questionList = csvQuestionDao.findAll();

        Assertions.assertEquals(5, questionList.size());
    }
}
