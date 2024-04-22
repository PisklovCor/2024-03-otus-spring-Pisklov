package ru.otus.hw.service;

import lombok.AllArgsConstructor;
import ru.otus.hw.dao.QuestionDao;
import ru.otus.hw.domain.Answer;
import ru.otus.hw.domain.Question;

import java.util.List;

@AllArgsConstructor
public class TestServiceImpl implements TestService {

    private final IOService ioService;

    private final QuestionDao questionDao;

    @Override
    public void executeTest() {

        ioService.printLine("");
        ioService.printLine("Please answer the questions below");

        List<Question> questionList = questionDao.findAll();

        for (Question question : questionList) {
            ioService.printFormattedLine(question.text(), getAnswerText(question.answers()));
        }
    }

    /**
     * Метод конвертирует список возможных ответов в String
     *
     * @param answers Список возможных ответов
     * @return Ответы одной строкой
     */
    private String getAnswerText(List<Answer> answers) {

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("-Answers:");

        for (Answer answer : answers) {
            stringBuilder.append("\n--").append(answer.text());
        }

        return stringBuilder.toString();
    }
}
