package ru.otus.hw.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.hw.models.Account;
import ru.otus.hw.models.AccountBook;

import java.util.List;

public interface AccountBookRepository extends JpaRepository<AccountBook, Long> {

    List<AccountBook> findAllByAccount(Account account);

}
