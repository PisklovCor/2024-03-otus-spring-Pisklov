package ru.otus.hw.repositories.primary;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.hw.models.primary.Comment;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findAllByBookId(long bookId);

}
