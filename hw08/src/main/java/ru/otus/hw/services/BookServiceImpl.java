package ru.otus.hw.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.hw.converters.BookConverter;
import ru.otus.hw.dto.BookDto;
import ru.otus.hw.exceptions.EntityNotFoundException;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Genre;
import ru.otus.hw.repositories.AuthorRepository;
import ru.otus.hw.repositories.BookRepository;
import ru.otus.hw.repositories.GenreRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.springframework.util.CollectionUtils.isEmpty;

@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookService {

    private final BookConverter bookConverter;

    private final AuthorRepository authorRepository;

    private final GenreRepository genreRepository;

    private final BookRepository bookRepository;

    @Override
    public List<BookDto> findAll() {
        return bookRepository.findAll().stream().map(bookConverter::toDto).toList();
    }

    @Override
    public Optional<BookDto> findById(String id) {
        return bookRepository.findById(id).stream().map(bookConverter::toDto).findAny();
    }

    @Override
    public BookDto create(String title, String authorId, Set<String> genresIds) {
        return bookConverter.toDto(bookRepository.save(
                new Book(null, title, checkingAndSearchingAuthor(authorId),
                        checkingAndSearchingGenres(genresIds))));
    }

    @Override
    public BookDto update(String id, String title, String authorId, Set<String> genresIds) {

        var book = bookRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Book with id %s not found".formatted(id)));

        book.setTitle(title);
        book.setAuthor(checkingAndSearchingAuthor(authorId));
        book.setGenres(checkingAndSearchingGenres(genresIds));

        return bookConverter.toDto(bookRepository.save(book));
    }

    @Override
    public void deleteById(String id) {
        bookRepository.deleteById(id);
    }

    private Author checkingAndSearchingAuthor(String authorId) {

        return authorRepository.findById(authorId)
                .orElseThrow(() -> new EntityNotFoundException("Author with id %s not found".formatted(authorId)));
    }

    private List<Genre> checkingAndSearchingGenres(Set<String> genresIds) {
        if (isEmpty(genresIds)) {
            throw new IllegalArgumentException("Genres ids must not be null");
        }

        var genres = genreRepository.findAllById(genresIds);
        if (isEmpty(genres) || genresIds.size() != genres.size()) {
            throw new EntityNotFoundException("One or all genres with ids %s not found".formatted(genresIds));
        }

        return genres;
    }
}
