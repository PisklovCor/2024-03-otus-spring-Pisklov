package ru.otus.hw.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProcessingRawMessageScheduledTasksImpl implements ScheduledTasks {

    private final ProcessingRawMessageService service;

    @Override
    @Scheduled(fixedDelayString = "PT010S")
    public void run() {

        log.info("Start ProcessingRawMessageScheduledTasksImpl: {}", LocalTime.now());
        service.processingRawMessage();
        log.info("Completion ProcessingRawMessageScheduledTasksImpl: {}", LocalTime.now());

    }
}
