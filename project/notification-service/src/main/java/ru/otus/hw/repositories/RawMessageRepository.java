package ru.otus.hw.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.hw.dictionaries.Status;
import ru.otus.hw.models.RawMessage;

import java.util.List;

public interface RawMessageRepository extends JpaRepository<RawMessage, Long> {

    List<RawMessage> findAllByStatus(Status status);
}
