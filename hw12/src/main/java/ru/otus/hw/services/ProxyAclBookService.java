package ru.otus.hw.services;

import ru.otus.hw.models.Book;

import java.util.List;

/**
 * Прокси сервис что бы исключить несколько ошибко:
 * - LazyInitializationException (использовать маппер на уровне сервиса, а не контроллера)
 * - Внедрение аспектов springframework.security.acls
 */
public interface ProxyAclBookService {

    List<Book> findAll();

    Book findById(long id);

    Book create(Book bookEntity);

    Book update(Book bookEntity);

    void deleteById(Book bookEntity);
}
