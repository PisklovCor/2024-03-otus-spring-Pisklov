package ru.otus.hw.repositories.primary;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.hw.models.primary.Author;

public interface AuthorRepository extends JpaRepository<Author, Long> {
}
