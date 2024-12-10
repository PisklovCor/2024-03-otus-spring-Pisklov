package ru.otus.hw.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.hw.dictionaries.Status;
import ru.otus.hw.models.Order;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findAllByLogin(String login);

    List<Order> findAllByStatus(Status status);
}
