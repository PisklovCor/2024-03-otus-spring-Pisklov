package ru.otus.hw.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw.clients.AccountClient;
import ru.otus.hw.models.MessageUser;
import ru.otus.hw.models.RawMessage;
import ru.otus.hw.repositories.MessageUserRepository;
import ru.otus.hw.repositories.RawMessageRepository;
import ru.otus.hw.utils.GenerateMessageContent;

import static ru.otus.hw.dictionaries.Status.CREATED;
import static ru.otus.hw.dictionaries.Status.WAIT;
import static ru.otus.hw.dictionaries.Status.CONFIRMED;
import static ru.otus.hw.dictionaries.Status.ERROR;

/**
 * Логика сервиса реализована для ознакомления и применения подходов из доклада: Используем @Transactional like a Pro
 * <a href="https://squidex.jugru.team/api/assets/srm/571d18e0-76b9-4910-ae3c-5508cdeda541/jpoint-letovnk.pdf">...</a>
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ProcessingRawMessageService {

    private final RawMessageRepository rawMessageRepository;

    private final MessageUserRepository messageUserRepository;

    private final AccountClient client;

    @Transactional
    public void processingRawMessage() {

        var rawMessageList = rawMessageRepository.findAllByStatus(CREATED);

        for (RawMessage rawMessage : rawMessageList) {
            rawMessageRepository.findById(rawMessage.getId()).ifPresentOrElse(raw -> {
                raw.setStatus(WAIT);
                rawMessageRepository.save(raw);
                log.info("rawMessage: {} update status: {}", rawMessage.getId(), rawMessage.getStatus());

                try {
                    makeAndSaveMessageUser(raw);

                    raw.setStatus(CONFIRMED);
                    rawMessageRepository.save(raw);
                    log.info("rawMessage: {} update status: {}", rawMessage.getId(), rawMessage.getStatus());
                } catch (Exception e) {
                    setErrorStatus(rawMessage);
                }
            }, () -> {
                setErrorStatus(rawMessage);
            });
        }
    }

    private void makeAndSaveMessageUser(RawMessage rawMessage) {

        val accountDto = client.getAccountByLogin(rawMessage.getLogin());

        messageUserRepository.save(MessageUser.builder()
                .login(rawMessage.getLogin())
                .mail(accountDto.getMail())
                .message(GenerateMessageContent.generateMessageContentUser(rawMessage, accountDto))
                .status(CREATED)
                .build());
    }

    private void setErrorStatus(RawMessage rawMessage) {
        rawMessage.setStatus(ERROR);
        rawMessageRepository.save(rawMessage);
        log.warn("rawMessage: {} update status: {}", rawMessage.getId(), rawMessage.getStatus());
    }
}
