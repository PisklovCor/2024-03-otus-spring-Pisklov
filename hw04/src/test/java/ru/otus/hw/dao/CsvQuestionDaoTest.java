package ru.otus.hw.dao;

import lombok.val;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.otus.hw.SpringBootApplicationTest;

@DisplayName("Сервис для работы с файловой системой ")
class CsvQuestionDaoTest extends SpringBootApplicationTest {

    @Autowired
    private QuestionDao questionDao;

    @DisplayName("должен проверить количество вопросов")
    @Test
    void findAll() {
        val exampleQuestionList = questionDao.findAll();

        Assertions.assertEquals(5, exampleQuestionList.size());
    }
}
