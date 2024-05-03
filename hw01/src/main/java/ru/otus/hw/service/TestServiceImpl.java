package ru.otus.hw.service;

import lombok.AllArgsConstructor;
import ru.otus.hw.dao.QuestionDao;
import ru.otus.hw.domain.Answer;
import ru.otus.hw.domain.Question;

import java.util.List;

@AllArgsConstructor
public class TestServiceImpl implements TestService {

    private static final String CHOOSE_THE_QUESTION = "Choose the question:";

    private static final String CARRIAGE_TRANSFER = "\n";

    private final IOService ioService;

    private final QuestionDao questionDao;

    @Override
    public void executeTest() {

        ioService.printLine("");
        ioService.printLine("Please answer the questions below");

        List<Question> questionList = questionDao.findAll();

        for (Question question : questionList) {
            ioService.printLine(CARRIAGE_TRANSFER + question.text());
            ioService.printFormattedLine(CHOOSE_THE_QUESTION + "%s", getAnswerText(question.answers()));
        }
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
