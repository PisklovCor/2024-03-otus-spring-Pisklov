package ru.otus.hw.repositories.secondary;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ru.otus.hw.models.secondary.Book;

@Repository
public interface BookMongoRepository extends MongoRepository<Book, String> {
}
