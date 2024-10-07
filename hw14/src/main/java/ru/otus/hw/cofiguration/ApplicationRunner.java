package ru.otus.hw.cofiguration;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import ru.otus.hw.services.InstrumentService;

@Profile("!test")
@Component
@RequiredArgsConstructor
public class ApplicationRunner implements CommandLineRunner {

    private final InstrumentService instrumentService;

    @Override
    public void run(String... args) {
        instrumentService.startGenerateRawMessageLoop();
    }
}
