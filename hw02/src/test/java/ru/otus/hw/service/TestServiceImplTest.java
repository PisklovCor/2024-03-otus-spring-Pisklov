package ru.otus.hw.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import ru.otus.hw.dao.CsvQuestionDao;
import ru.otus.hw.dao.QuestionDao;
import ru.otus.hw.domain.Answer;
import ru.otus.hw.domain.Question;
import ru.otus.hw.domain.Student;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
@DisplayName("Сервис тестирования стендета ")
class TestServiceImplTest {

    private static final String STUDENT_FULL_NAME = "Student FullName";

    @Mock
    private StreamsIOService ioService;

    @Mock
    private CsvQuestionDao questionDao;

    @InjectMocks
    private TestServiceImpl testService;

    @DisplayName("должен вернуть корректный результат тестирования и имя")
    @Test
    void executeTestFor() {

        List<Question> questionList = Collections.singletonList(new Question("test",
                Collections.singletonList(new Answer("text", true))));
        given(questionDao.findAll()).willReturn(questionList);

        given(ioService.readIntForRangeWithPrompt(1, questionList.size(), "Select an answer option:",
                "A non-existent option was selected!"))
                .willReturn(1);

        final Student student = new Student("Student", "FullName");
        var testResult = testService.executeTestFor(student);

        Assertions.assertEquals(STUDENT_FULL_NAME, testResult.getStudent().getFullName());
        Assertions.assertEquals(1, testResult.getRightAnswersCount());
    }
}