package ru.otus.hw;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import ru.otus.hw.service.TestRunnerServiceImpl;

@ComponentScan
public class Application {
    public static void main(String[] args) {

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Application.class);
        var testRunnerService = context.getBean(TestRunnerServiceImpl.class);
        testRunnerService.run();
    }
}