package ru.otus.hw.repositories;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import ru.otus.hw.models.Book;

public interface BookRepository extends ReactiveMongoRepository<Book, String> {

}
