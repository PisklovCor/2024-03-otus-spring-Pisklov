package ru.otus.hw.repositories;

import jakarta.persistence.PersistenceContext;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityGraph;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.otus.hw.models.Book;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class JpaBookRepository implements BookRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Book> findAll() {

        EntityGraph<?> entityGraph = em.getEntityGraph("genres-entity-graph");

        TypedQuery<Book> query = em.createQuery("select b from Book b left join fetch b.author", Book.class);

        query.setHint("jakarta.persistence.fetchgraph", entityGraph);

        return query.getResultList();
    }

    @Override
    public Optional<Book> findById(long id) {

        EntityGraph<?> entityGraph = em.getEntityGraph("genres-entity-graph");

        TypedQuery<Book> query = em.createQuery("select b " +
                        "from Book b " +
                        "left join fetch b.author " +
                        "where b.id = :id", Book.class);

        query.setParameter("id", id);
        query.setHint("jakarta.persistence.fetchgraph", entityGraph);

        return query.getResultList().stream().findAny();
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
        Optional<Book> book = findById(id);
        em.remove(book.get());
    }
}
