package ru.otus.hw.cofiguration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.JobRepositoryTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import ru.otus.hw.repositories.secondary.BookMongoRepository;

import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.otus.hw.cofiguration.JobConfig.MIGRATION_DATA_JOB_NAME;

@DisplayName("Миграции данных из реляционного хранилища в NoSQL ")
@SpringBootTest
@SpringBatchTest
class MigrationDataJobTest {

    private static final int EXPECTED_COUNT_BOOKS = 3;
    private static final int EXPECTED_COUNT_GENRES = 2;
    private static final String STATUS_COMPLETED_JOB = "COMPLETED";
    Pattern UUID_REGEX =
            Pattern.compile("^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$");

    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    @Autowired
    private JobRepositoryTestUtils jobRepositoryTestUtils;

    @Autowired
    private BookMongoRepository bookMongoRepository;

    @BeforeEach
    void clearMetaData() {
        jobRepositoryTestUtils.removeJobExecutions();
    }

    @DisplayName("должна перенести 3 таблицы")
    @Test
    void migrationDataJobTest() throws Exception {

        Job job = jobLauncherTestUtils.getJob();
        assertThat(job).isNotNull()
                .extracting(Job::getName)
                .isEqualTo(MIGRATION_DATA_JOB_NAME);

        JobParameters parameters = new JobParametersBuilder().toJobParameters();
        JobExecution jobExecution = jobLauncherTestUtils.launchJob(parameters);

        assertThat(jobExecution.getExitStatus().getExitCode()).isEqualTo(STATUS_COMPLETED_JOB);

        var expectedBooks = bookMongoRepository.findAll();

        assertThat(expectedBooks).isNotNull().hasSize(EXPECTED_COUNT_BOOKS)
                .allMatch(b -> UUID_REGEX.matcher(b.getId()).matches())
                .allMatch(b -> !b.getTitle().isEmpty())
                .allMatch(b -> b.getGenres().size() == EXPECTED_COUNT_GENRES);
    }
}