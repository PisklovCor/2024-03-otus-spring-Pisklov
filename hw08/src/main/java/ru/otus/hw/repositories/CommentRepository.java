package ru.otus.hw.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.otus.hw.models.Comment;

import java.util.List;

public interface CommentRepository  extends MongoRepository<Comment, String> {

    @Query("{'book.id' : :#{#bookId}}")
    List<Comment> findAllByBookId(@Param("bookId") String bookId);
}
