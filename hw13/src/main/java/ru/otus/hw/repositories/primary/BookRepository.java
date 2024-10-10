package ru.otus.hw.repositories.primary;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.hw.models.primary.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long>  {

    @Override
    @EntityGraph("book-entity-graph")
    List<Book> findAll();

    @Override
    @EntityGraph("book-entity-graph-author")
    Optional<Book> findById(Long id);

}
