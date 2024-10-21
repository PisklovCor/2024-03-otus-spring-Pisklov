package ru.otus.hw.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.hw.dao.QuestionDao;
import ru.otus.hw.domain.Answer;
import ru.otus.hw.domain.Student;
import ru.otus.hw.domain.TestResult;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TestServiceImpl implements TestService {

    private static final String CHOOSE_THE_QUESTION = "TestService.choose.question.message";

    private static final String CARRIAGE_TRANSFER = "\n";

    private static final int MIN_SIZE = 1;

    private final LocalizedIOService ioService;

    private final QuestionDao questionDao;

    @Override
    public TestResult executeTestFor(Student student) {
        ioService.printLine("");
        ioService.printFormattedLineLocalized("TestService.answer.the.questions");
        var questions = questionDao.findAll();
        var testResult = new TestResult(student);

        for (var question : questions) {
            ioService.printLine(CARRIAGE_TRANSFER + question.text());
            ioService.printFormattedLineLocalized(CHOOSE_THE_QUESTION, getAnswerText(question.answers()));

            var maxSize = question.answers().size();

            var answerIndex = ioService.readIntForRangeWithPromptLocalized(MIN_SIZE, maxSize,
                    "TestService.choose.an.answer", "TestService.error.message");

            var isAnswerValid = question.answers().get(answerIndex - 1).isCorrect();
            testResult.applyAnswer(question, isAnswerValid);
        }
        return testResult;
    }

    /**
     * Метод конвертирует список возможных ответов в String и добавляет нумерацию ответов
     *
     * @param answers Список возможных ответов
     * @return Ответы одной строкой
     */
    private String getAnswerText(List<Answer> answers) {

        StringBuilder stringBuilder = new StringBuilder();
        long lineNumber = 1;

        for (Answer answer : answers) {
            stringBuilder.append(CARRIAGE_TRANSFER).append(lineNumber).append("--").append(answer.text());
            lineNumber++;
        }

        stringBuilder.append(CARRIAGE_TRANSFER).append("_____");
        return stringBuilder.toString();
    }
}
