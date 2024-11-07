package ru.otus.hw.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.hw.SpringBootApplicationTest;
import ru.otus.hw.dao.CsvQuestionDao;
import ru.otus.hw.domain.Answer;
import ru.otus.hw.domain.Question;
import ru.otus.hw.domain.Student;

import java.util.Collections;
import java.util.List;

import static org.mockito.BDDMockito.given;

@DisplayName("Сервис тестирования студента ")
class TestServiceImplTest extends SpringBootApplicationTest {

    private static final String STUDENT_FULL_NAME = "FirstName LastName";

    @MockBean
    private LocalizedIOService ioService;

    @MockBean
    private CsvQuestionDao questionDao;

    @Autowired
    private TestService testService;

    @DisplayName("должен вернуть корректный результат тестирования и имя")
    @Test
    void executeTestFor() {

        List<Question> questionList = Collections.singletonList(new Question("test",
                List.of(new Answer("text", true))));
        given(questionDao.findAll()).willReturn(questionList);

        given(ioService.readIntForRangeWithPromptLocalized(1, questionList.size(),
                "TestService.choose.an.answer","TestService.error.message"))
                .willReturn(1);

        final Student student = new Student("FirstName", "LastName");
        var testResult = testService.executeTestFor(student);

        Assertions.assertEquals(STUDENT_FULL_NAME, testResult.getStudent().getFullName());
        Assertions.assertEquals(1, testResult.getRightAnswersCount());
    }
}