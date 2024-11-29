package ru.otus.hw.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import ru.otus.hw.exceptions.NotFoundException;
import ru.otus.hw.models.Book;
import ru.otus.hw.repositories.BookRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ProxyAclBookServiceImpl implements ProxyAclBookService {

    private final BookRepository bookRepository;

    private final PostProcessInstallingPermission postProcessInstallingPermission;

    @PostFilter("hasPermission(filterObject, 'READ')")
    @Override
    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    /**
     * Обработка исключение вынесена сюда т.к. hasPermission не умеет обрабатывать Optional
     */
    @PostAuthorize("hasPermission(returnObject, 'READ')")
    @Override
    public Book findById(long id) {
        return bookRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Book with id %d not found".formatted(id)));
    }

    @Override
    public Book create(Book bookEntity) {

        var newBookEntity = bookRepository.save(bookEntity);

        postProcessInstallingPermission.settingRights(newBookEntity);

        return newBookEntity;
    }

    @PreAuthorize("hasPermission(#bookEntity, 'WRITE')")
    @Override
    public Book update(Book bookEntity) {
        return bookRepository.save(bookEntity);
    }

    @PreAuthorize("hasPermission(#bookEntity, 'DELETE')")
    @Override
    public void deleteById(Book bookEntity) {
        bookRepository.deleteById(bookEntity.getId());
    }
}
