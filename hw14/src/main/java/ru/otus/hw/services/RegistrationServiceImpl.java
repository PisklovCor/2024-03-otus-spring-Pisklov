package ru.otus.hw.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.otus.hw.domain.Instrument;
import ru.otus.hw.domain.RawMessage;

import java.util.UUID;

import static ru.otus.hw.domain.InstrumentType.BOND;
import static ru.otus.hw.domain.InstrumentType.SHARE;

@Slf4j
@Service
public class RegistrationServiceImpl implements RegistrationService {

    @Override
    public Instrument registration(RawMessage message) {
        log.info("--Registration {}", message);

        var price = message.getPrice();
        var quantity = message.getQuantity();
        var nominalValue = price * quantity;

        var instrument = new Instrument();
        instrument.setInstrumentId(UUID.randomUUID());
        instrument.setInstrumentType(message.getInstrumentType().equalsIgnoreCase(BOND.getName()) ? BOND : SHARE);
        instrument.setPrice(price);
        instrument.setQuantity(quantity);
        instrument.setNominalValue(nominalValue);
        instrument.setSource(message.getSource());

        log.info("----Registration sInstrumentId={}", instrument.getInstrumentId());

        return instrument;
    }
}
