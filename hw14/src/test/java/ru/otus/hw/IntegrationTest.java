package ru.otus.hw;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.otus.hw.domain.Instrument;
import ru.otus.hw.services.InstrumentGateway;
import ru.otus.hw.services.InstrumentService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.otus.hw.domain.InstrumentType.BOND;
import static ru.otus.hw.domain.InstrumentType.SHARE;

@DisplayName("Флоу для обработки сообщений по инструментам ")
@SuppressWarnings("unused")
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class IntegrationTest {

    @Autowired
    private InstrumentService instrumentService;

    @Autowired
    private InstrumentGateway instrumentGateway;

    private static final List<String> MOCK_STRING_RAW_MESSAGE = new ArrayList<>();
    private static final List<String> MOCK_STRING_RAW_MESSAGE_BOND = new ArrayList<>();
    private static final List<String> MOCK_STRING_RAW_MESSAGE_SHARE = new ArrayList<>();

    private static final long BOND_PRICE = 100;
    private static final long BOND_QUANTITY = 200;
    private static final long SHARE_PRICE  = 300;
    private static final long SHARE_QUANTITY = 400;
    private static final String SOURCE = "registrationChannelTest";

    @BeforeAll
    static void init() {

        final String jsonRawMessageBond = """
                {
                \"instrument_type\" : \"BOND\",
                \"price\" : 100,
                \"quantity\" : 200,
                \"source\" : \"registrationChannelTest\"
                }""";

        final String jsonRawMessageShare = """
                {
                \"instrument_type\" : \"SHARE\",
                \"price\" : 300,
                \"quantity\" : 400,
                \"source\" : \"registrationChannelTest\"
                }""";


        MOCK_STRING_RAW_MESSAGE.add(jsonRawMessageBond);
        MOCK_STRING_RAW_MESSAGE.add(jsonRawMessageShare);

        MOCK_STRING_RAW_MESSAGE_BOND.add(jsonRawMessageBond);
        MOCK_STRING_RAW_MESSAGE_SHARE.add(jsonRawMessageShare);
    }

    @Order(1)
    @DisplayName("должен зарегистрировать облигацию (BOND)")
    @Test
    void processTestBond() {

        Collection<Instrument> instrumentResult = instrumentGateway.process(MOCK_STRING_RAW_MESSAGE_BOND);

        assertThat(instrumentResult).isNotNull().hasSize(1)
                .allMatch(i -> i.getInstrumentId() != null)
                .allMatch(i -> i.getInstrumentType() == BOND)
                .allMatch(i -> i.getPrice() == BOND_PRICE)
                .allMatch(i -> i.getQuantity() == BOND_QUANTITY)
                .allMatch(i -> i.getNominalValue() == BOND_PRICE * BOND_QUANTITY)
                .allMatch(i -> i.getSource().equals(SOURCE));
    }

    @Order(2)
    @DisplayName("должен зарегистрировать акцию (SHARE)")
    @Test
    void processTestShare() {

        Collection<Instrument> instrumentResult = instrumentGateway.process(MOCK_STRING_RAW_MESSAGE_SHARE);

        assertThat(instrumentResult).isNotNull().hasSize(1)
                .allMatch(i -> i.getInstrumentId() != null)
                .allMatch(i -> i.getInstrumentType() == SHARE)
                .allMatch(i -> i.getPrice() == SHARE_PRICE)
                .allMatch(i -> i.getQuantity() == SHARE_QUANTITY)
                .allMatch(i -> i.getNominalValue() == SHARE_PRICE * SHARE_QUANTITY)
                .allMatch(i -> i.getSource().equals(SOURCE));
    }

    @Order(3)
    @DisplayName("должен зарегистрировать все инструменты")
    @Test
    void processTestAll() {

        Collection<Instrument> instrumentResult = instrumentGateway.process(MOCK_STRING_RAW_MESSAGE);

        assertThat(instrumentResult).isNotNull().hasSize(2)
                .allMatch(i -> i.getInstrumentId() != null)
                .allMatch(i -> i.getInstrumentType() == SHARE
                        || i.getInstrumentType() == BOND)
                .allMatch(i -> i.getSource().equals(SOURCE));
    }
}
