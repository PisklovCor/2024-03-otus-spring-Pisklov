package ru.otus.hw.services;

import ru.otus.hw.domain.Instrument;

public interface TradingService {

    Instrument sendingToTradingSystemBond(Instrument instrument);

    Instrument sendingToTradingSystemShare(Instrument instrument);
}
