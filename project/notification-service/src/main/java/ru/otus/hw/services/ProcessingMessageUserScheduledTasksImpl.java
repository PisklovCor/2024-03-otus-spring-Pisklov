package ru.otus.hw.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProcessingMessageUserScheduledTasksImpl implements ScheduledTasks {

    private final ProcessingMessageUserService service;

    @Override
    @Scheduled(fixedDelayString = "PT015S")
    public void run() {
        log.info("Start ProcessingMessageUserScheduledTasksImpl: {}", LocalTime.now());
        service.processingMessageUser();
        log.info("Completion ProcessingMessageUserScheduledTasksImpl: {}", LocalTime.now());
    }
}
