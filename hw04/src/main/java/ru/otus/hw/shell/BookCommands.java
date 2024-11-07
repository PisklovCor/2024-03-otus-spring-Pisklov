package ru.otus.hw.shell;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.hw.domain.Student;
import ru.otus.hw.domain.TestResult;
import ru.otus.hw.service.ResultService;
import ru.otus.hw.service.TestRunnerService;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

@SuppressWarnings({"SpellCheckingInspection", "unused"})
@RequiredArgsConstructor
@ShellComponent
public class BookCommands {

    private final Cache<Student, TestResult> resultStudentCach = CacheBuilder.newBuilder()
            .expireAfterWrite(5, TimeUnit.MINUTES)
            .build();

    private final TestRunnerService testRunnerService;

    private final ResultService resultService;

    @ShellMethod(value = "Start testing students", key = {"s", "start"})
    public String startTest() {
        val result = testRunnerService.run();
        val student = result.getStudent();

        resultStudentCach.put(student, result);

        return String.format("Student: %s testing completed. You can view the result or take the test again.",
                student.getFullName());
    }

    @ShellMethod(value = "View test results", key = {"r", "result"})
    public String printResult(@ShellOption(defaultValue = "firstName") String firstName,
                              @ShellOption(defaultValue = "lastName") String lastName) throws ExecutionException {

        var student = new Student(firstName, lastName);

        resultService.showResult(resultStudentCach.get(student, new Callable<TestResult>() {
            @Override
            public TestResult call()  {
                throw new RuntimeException("Element testResult not found in resultStudentCach");
            }
        }));

        return "Test results:";
    }

    @ShellMethod(value = "View saved test results", key = {"c", "cach"})
    public String lookResultStudentCach() {
        return  String.format("resultStudentCach: [%s]", resultStudentCach.asMap());
    }
}
