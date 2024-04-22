package ru.otus.hw.dao;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;
import lombok.RequiredArgsConstructor;
import ru.otus.hw.config.TestFileNameProvider;
import ru.otus.hw.dao.dto.QuestionDto;
import ru.otus.hw.domain.Question;
import ru.otus.hw.exceptions.QuestionReadException;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class CsvQuestionDao implements QuestionDao {

    private final TestFileNameProvider fileNameProvider;

    @Override
    public List<Question> findAll() {
        try {
            InputStream is = CsvQuestionDao.class.getClassLoader()
                    .getResourceAsStream(fileNameProvider.getTestFileName());
            final CSVReader csvReader = new CSVReaderBuilder(new BufferedReader(new InputStreamReader(is)))
                    .withSkipLines(1).build();

            CsvToBean csv = new CsvToBean();
            csv.setCsvReader(csvReader);
            csv.setMappingStrategy(setColumMapping());
            List list = csv.parse();
            List<Question> questionList = new ArrayList<>();

            for (Object object : list) {
                Question question = ((QuestionDto) object).toDomainObject();
                questionList.add(question);
            }
            return questionList;

        } catch (Exception e) {
            throw new QuestionReadException("Error during CSV conversion", e);
        }
    }

    private static ColumnPositionMappingStrategy setColumMapping() {
        ColumnPositionMappingStrategy strategy = new ColumnPositionMappingStrategy();
        strategy.setType(QuestionDto.class);
        String[] columns = new String[]{"text", "answers"};
        strategy.setColumnMapping(columns);
        return strategy;
    }
}
