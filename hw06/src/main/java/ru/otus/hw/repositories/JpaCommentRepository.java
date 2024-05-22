package ru.otus.hw.repositories;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.otus.hw.models.Comment;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class JpaCommentRepository implements CommentRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Optional<Comment> findById(long id) {

        TypedQuery<Comment> query = em.createQuery("select c " +
                "from Comment c " +
                "left join fetch c.book " +
                "where c.id = :id", Comment.class);

        query.setParameter("id", id);

        return query.getResultList().stream().findAny();
    }

    @Override
    public List<Comment> findAllByBookId(long bookId) {

        TypedQuery<Comment> query = em.createQuery("select c " +
                "from Comment c " +
                "left join fetch c.book " +
                "where c.book.id = :id", Comment.class);

        query.setParameter("id", bookId);

        return query.getResultList();
    }

    @Override
    public Comment save(Comment comment) {
        if (comment.getId() == 0) {
            em.persist(comment);
            return comment;
        } else {
            return em.merge(comment);
        }
    }

    @Override
    public void deleteById(long id) {
        Optional<Comment> comment = findById(id);
        em.remove(comment.get());
    }
}
