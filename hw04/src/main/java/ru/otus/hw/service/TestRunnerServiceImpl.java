package ru.otus.hw.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.hw.domain.TestResult;

@Service
@RequiredArgsConstructor
public class TestRunnerServiceImpl implements TestRunnerService {

    private final TestService testService;

    private final StudentService studentService;

    @Override
    public TestResult run(String... args) {
        var student = studentService.determineCurrentStudent();
        return testService.executeTestFor(student);
    }
}
