package ru.otus.hw.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.hw.dao.CsvQuestionDao;
import ru.otus.hw.domain.Answer;
import ru.otus.hw.domain.Question;
import ru.otus.hw.domain.Student;

import java.util.Collections;
import java.util.List;

import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
@DisplayName("Сервис тестирования студента ")
class TestServiceImplTest {

    private static final String STUDENT_FULL_NAME = "FirstName LastName";

    @Mock
    private LocalizedIOService ioService;

    @Mock
    private CsvQuestionDao questionDao;

    @InjectMocks
    private TestServiceImpl testService;

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