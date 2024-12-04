package ru.otus.hw.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.hw.dictionaries.Status;
import ru.otus.hw.models.MessageUser;

import java.util.List;

public interface MessageUserRepository extends JpaRepository<MessageUser, Long> {

    List<MessageUser> findAllByLogin(String login);

    List<MessageUser> findAllByStatus(Status status);
}
