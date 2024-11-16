package ru.otus.hw.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.hw.models.AccountBook;

public interface AccountBookRepository extends JpaRepository<AccountBook, Long> {

}
