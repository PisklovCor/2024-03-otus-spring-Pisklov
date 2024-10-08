package ru.otus.hw.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.stereotype.Service;
import ru.otus.hw.domain.Instrument;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class InstrumentServiceImpl implements InstrumentService {

    private static final String[] INSTRUMENT_TYPE = {"BOND", "SHARE"};

    private final InstrumentGateway instrumentGateway;

    @Override
    public void startGenerateRawMessageLoop() {
        ForkJoinPool pool = ForkJoinPool.commonPool();
        for (int i = 0; i < 10; i++) {
            int num = i + 1;
            pool.execute(() -> {
                Collection<String> items = generateRawMessages();
                log.info("{}, New jsonRawMessage size: {}", num, items.size());
                Collection<Instrument> instrument = instrumentGateway.process(items);
                log.info("{}, Ready RawMessages: {}", num, instrument.stream()
                        .map(inst -> inst.getInstrumentId().toString())
                        .collect(Collectors.joining(",")));
            });
            delay();
        }
    }

    private Collection<String> generateRawMessages() {
        List<String> items = new ArrayList<>();
        for (int i = 0; i < RandomUtils.nextInt(1, 5); ++i) {
            items.add(generateStringRawMessage());
        }
        return items;
    }

    /**
     * Создание json объекта с рандомными полями
     *
     * @return json
     */
    private String generateStringRawMessage() {

        String jsonRawMessage = """
                {\"instrument_type\" : \"%s\", \"price\" : %d, \"quantity\" : %d, \"source\" : \"%s\"}""";

        return String.format(jsonRawMessage,
                INSTRUMENT_TYPE[RandomUtils.nextInt(0, INSTRUMENT_TYPE.length)],
                RandomUtils.nextInt(0, 100),
                RandomUtils.nextInt(0, 10000),
                "registrationChannel");
    }

    private void delay() {
        try {
            Thread.sleep(7000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
