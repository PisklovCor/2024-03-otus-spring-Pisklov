package ru.otus.hw.cofiguration;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import ru.otus.hw.services.InstrumentService;

@Slf4j
@Component
@RequiredArgsConstructor
public class ApplicationRunner implements CommandLineRunner {

    private final InstrumentService instrumentService;

    @Override
    public void run(String... args) {
        instrumentService.startGenerateRawMessageLoop();
    }
}
