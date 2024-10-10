package ru.otus.hw.repositories.primary;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.hw.models.primary.Genre;

public interface GenreRepository extends JpaRepository<Genre, Long> {

}
