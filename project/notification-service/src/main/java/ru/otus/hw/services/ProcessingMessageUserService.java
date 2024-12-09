package ru.otus.hw.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw.models.MessageUser;
import ru.otus.hw.repositories.MessageUserRepository;

import static ru.otus.hw.dictionaries.Status.CONFIRMED;
import static ru.otus.hw.dictionaries.Status.CREATED;
import static ru.otus.hw.dictionaries.Status.ERROR;
import static ru.otus.hw.dictionaries.Status.WAIT;

/**
 * Логика сервиса реализована для ознакомления и применения подходов из доклада: Используем @Transactional like a Pro
 * <a href="https://squidex.jugru.team/api/assets/srm/571d18e0-76b9-4910-ae3c-5508cdeda541/jpoint-letovnk.pdf">...</a>
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ProcessingMessageUserService {

    private final MessageUserRepository repository;

    @Transactional
    public void processingMessageUser() {

        var messageUserList = repository.findAllByStatus(CREATED);

        for (MessageUser messageUser : messageUserList) {
            repository.findById(messageUser.getId()).ifPresentOrElse(raw -> {
                raw.setStatus(WAIT);
                repository.save(raw);
                log.info("messageUser: {} update status: {}", messageUser.getId(), messageUser.getStatus());

                try {

                    Thread.sleep(2000);

                    raw.setStatus(CONFIRMED);
                    repository.save(raw);
                    log.info("rawMessage: {} update status: {}", messageUser.getId(), messageUser.getStatus());
                } catch (Exception e) {
                    setErrorStatus(messageUser);
                }
            }, () -> {
                setErrorStatus(messageUser);
            });
        }
    }

    private void setErrorStatus(MessageUser messageUser) {
        messageUser.setStatus(ERROR);
        repository.save(messageUser);
        log.warn("rawMessage: {} update status: {}", messageUser.getId(), messageUser.getStatus());
    }
}
