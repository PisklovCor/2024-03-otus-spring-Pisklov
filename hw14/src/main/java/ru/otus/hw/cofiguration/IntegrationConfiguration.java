package ru.otus.hw.cofiguration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.integration.dsl.MessageChannelSpec;
import org.springframework.integration.dsl.MessageChannels;
import org.springframework.integration.dsl.PollerSpec;
import org.springframework.integration.dsl.Pollers;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.scheduling.PollerMetadata;
import ru.otus.hw.domain.Instrument;
import ru.otus.hw.domain.InstrumentType;
import ru.otus.hw.services.RegistrationService;
import ru.otus.hw.services.TradingService;

import static ru.otus.hw.domain.InstrumentType.BOND;
import static ru.otus.hw.domain.InstrumentType.SHARE;

@Configuration
public class IntegrationConfiguration {

    @Bean
    public MessageChannelSpec<?, ?> registrationChannel() {
        return MessageChannels.queue(10);
    }

    @Bean
    public MessageChannelSpec<?, ?> tradeChannel() {
        return MessageChannels.publishSubscribe();
    }

    @Bean(name = PollerMetadata.DEFAULT_POLLER)
    public PollerSpec poller() {
        return Pollers.fixedRate(100).maxMessagesPerPoll(2);
    }

    @Bean
    public IntegrationFlow instrumentFlow(RegistrationService registrationService,TradingService tradingService) {
        return IntegrationFlow.from(registrationChannel())
                .split()
                .handle(registrationService, "registration")
                .<Instrument, InstrumentType>route(Instrument::getInstrumentType,
                        mapping -> mapping
                                .subFlowMapping(BOND, sf -> sf.gateway(instrumentBondFlow(tradingService)))
                                .subFlowMapping(SHARE, sf -> sf.gateway(instrumentShareFlow(tradingService))))
                .aggregate()
                .get();
    }


    @Bean
    public IntegrationFlow instrumentBondFlow(TradingService tradingService) {
        return f -> f.handle(tradingService, "sendingToTradingSystemBond")
                .channel(tradeChannel());
    }

    @Bean
    public IntegrationFlow instrumentShareFlow(TradingService tradingService) {
        return f -> f.handle(tradingService, "sendingToTradingSystemShare")
                .channel(tradeChannel());
    }
}
