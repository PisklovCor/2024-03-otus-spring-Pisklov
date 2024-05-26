package ru.otus.hw.repositories;

import jakarta.persistence.PersistenceContext;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityGraph;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.otus.hw.exceptions.EntityNotFoundException;
import ru.otus.hw.models.Book;

import java.util.List;
import java.util.Optional;
import java.util.Map;
import java.util.HashMap;

@Repository
@RequiredArgsConstructor
public class JpaBookRepository implements BookRepository {

    private static final String FETCH_GRAPH_NAME = "book-entity-graph";

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Book> findAll() {

        EntityGraph<?> entityGraph = em.getEntityGraph(FETCH_GRAPH_NAME);

        TypedQuery<Book> query = em.createQuery("select b from Book b", Book.class);

        query.setHint("jakarta.persistence.fetchgraph", entityGraph);

        return query.getResultList();
    }

    @Override
    public Optional<Book> findById(long id) {

        EntityGraph<?> entityGraph = em.getEntityGraph(FETCH_GRAPH_NAME);
        Map<String, Object> properties = new HashMap<>();
        properties.put("jakarta.persistence.fetchgraph", entityGraph);

        return Optional.ofNullable(em.find(Book.class, id, properties));
    }

    @Override
    public Book save(Book book) {
        if (book.getId() == null) {
            em.persist(book);
            return book;
        } else {
            return em.merge(book);
        }
    }

    @Override
    public void deleteById(long id) {
        Optional<Book> book = Optional.ofNullable(em.find(Book.class, id));
        em.remove(book.orElseThrow(() -> new EntityNotFoundException("Book with id %d not found".formatted(id))));
    }
}
