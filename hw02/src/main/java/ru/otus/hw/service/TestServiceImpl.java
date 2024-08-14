package ru.otus.hw.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.hw.dao.QuestionDao;
import ru.otus.hw.domain.Answer;
import ru.otus.hw.domain.Student;
import ru.otus.hw.domain.TestResult;

import java.util.List;

@Service
@AllArgsConstructor
public class TestServiceImpl implements TestService {

    private static final String CHOOSE_THE_QUESTION = "Choose the question:";

    private static final String CARRIAGE_TRANSFER = "\n";

    private static final int MIN_SIZE = 1;

    private final IOService ioService;

    private final QuestionDao questionDao;

    @Override
    public TestResult executeTestFor(Student student) {
        ioService.printLine("");
        ioService.printFormattedLine("Please answer the questions below%n");
        var questions = questionDao.findAll();
        var testResult = new TestResult(student);

        for (var question : questions) {
            ioService.printLine(CARRIAGE_TRANSFER + question.text());
            ioService.printFormattedLine(CHOOSE_THE_QUESTION + "%s", getAnswerText(question.answers()));

            var maxSize = question.answers().size();

            var answerIndex = ioService.readIntForRangeWithPrompt(MIN_SIZE, maxSize, "Select an answer option:",
                    "A non-existent option was selected!");

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
