package ru.otus.hw.repositories;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.otus.hw.models.Book;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource(path = "book")
public interface BookRepository extends JpaRepository<Book, Long>  {

    @Override
    @EntityGraph("book-entity-graph")
    List<Book> findAll();

    @Override
    @EntityGraph("book-entity-graph-author")
    Optional<Book> findById(Long id);

}
