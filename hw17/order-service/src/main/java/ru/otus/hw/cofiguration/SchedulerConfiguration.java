package ru.otus.hw.cofiguration;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
@ConditionalOnProperty(name = "application.scheduler.enabled", matchIfMissing = true)
public class SchedulerConfiguration {
}
