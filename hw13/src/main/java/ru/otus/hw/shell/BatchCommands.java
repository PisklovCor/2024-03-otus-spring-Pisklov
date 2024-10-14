package ru.otus.hw.shell;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.batch.core.JobExecution;
import org.springframework.shell.standard.ShellMethod;

@Slf4j
@RequiredArgsConstructor
@ShellComponent
public class BatchCommands {

    private final Job migrationDataJob;

    private final JobLauncher jobLauncher;

    @SuppressWarnings("unused")
    @ShellMethod(value = "startMigrationJobWithJobLauncher", key = "sm-restart")
    public void startMigrationJobWithJobLauncher() throws Exception {
        JobExecution execution = jobLauncher.run(migrationDataJob, new JobParametersBuilder()
                .addLong("launchTime", System.currentTimeMillis())
                .toJobParameters());
        log.info(execution.toString());
    }
}
