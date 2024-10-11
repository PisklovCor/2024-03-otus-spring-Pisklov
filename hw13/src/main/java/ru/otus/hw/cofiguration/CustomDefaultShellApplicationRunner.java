package ru.otus.hw.cofiguration;

import org.springframework.core.annotation.Order;
import org.springframework.shell.DefaultShellApplicationRunner;
import org.springframework.shell.ShellRunner;
import org.springframework.stereotype.Component;

import java.util.List;

/** Создание дополнительного бина для увеличения очереди запуска ShellApplicationRunner
 * т.к. дефолтная реализация запускает внеочереди @Order(DefaultShellApplicationRunner.PRECEDENCE)
 * решение частично основано на статье: <a href="https://otus.ru/nest/post/1557">...</a>
 */
@Order(10)
@Component
public class CustomDefaultShellApplicationRunner extends DefaultShellApplicationRunner {

    public CustomDefaultShellApplicationRunner(List<ShellRunner> shellRunners) {
        super(shellRunners);
    }
}
