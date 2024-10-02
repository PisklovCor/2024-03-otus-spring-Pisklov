package ru.otus.hw.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.otus.hw.domain.Instrument;

@Service
@Slf4j
public class TradingServiceImpl implements TradingService {

    @Override
    public Instrument sendingToTradingSystemBond(Instrument instrument) {
        log.info("---Send an instrument BOND for auction {}",  instrument.getInstrumentId());
        return instrument;
    }

    @Override
    public Instrument sendingToTradingSystemShare(Instrument instrument) {
        log.info("---Send an instrument SHARE for auction {}",  instrument.getInstrumentId());
        return instrument;
    }
}
