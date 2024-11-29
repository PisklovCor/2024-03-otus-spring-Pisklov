package ru.otus.hw.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw.dto.BookUpdateDto;
import ru.otus.hw.mappers.BookMapper;
import ru.otus.hw.dto.BookCreateDto;
import ru.otus.hw.dto.BookDto;
import ru.otus.hw.dto.GenreDto;
import ru.otus.hw.exceptions.NotFoundException;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Genre;
import ru.otus.hw.repositories.AuthorRepository;
import ru.otus.hw.repositories.BookRepository;
import ru.otus.hw.repositories.GenreRepository;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.springframework.util.CollectionUtils.isEmpty;

@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookService {

    private final BookMapper bookMapper;

    private final AuthorRepository authorRepository;

    private final GenreRepository genreRepository;

    private final BookRepository bookRepository;

    private final ProxyAclBookService proxyAclBookService;

    @Override
    @Transactional(readOnly = true)
    public List<BookDto> findAll() {
        return proxyAclBookService.findAll().stream().map(bookMapper::toDto).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public BookDto findById(long id) {
        var bookEntity = proxyAclBookService.findById(id);

        return bookMapper.toDto(bookEntity);
    }

    @Override
    @Transactional
    public BookDto create(BookCreateDto dto) {
        return bookMapper.toDto(proxyAclBookService.create(
                new Book(null, dto.getTitle(), checkingAndSearchingAuthor(dto.getAuthor().getId()),
                        checkingAndSearchingGenres(dto.getGenres().stream()
                                .map(GenreDto::getId)
                                .collect(Collectors.toSet())))));
    }

    @Override
    @Transactional
    public BookDto update(BookUpdateDto dto) {

        final long bookId = dto.getId();

        var bookEntity = bookRepository.findById(bookId)
                .orElseThrow(() -> new NotFoundException("Book with id %d not found".formatted(bookId)));

        bookEntity.setTitle(dto.getTitle());
        bookEntity.setAuthor(checkingAndSearchingAuthor(dto.getAuthor().getId()));
        bookEntity.setGenres(checkingAndSearchingGenres(dto.getGenres().stream()
                .map(GenreDto::getId)
                .collect(Collectors.toSet())));

        return bookMapper.toDto(proxyAclBookService.update(bookEntity));
    }

    @Override
    @Transactional
    public void deleteById(long id) {
        var bookEntity = bookRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Book with id %d not found".formatted(id)));

        proxyAclBookService.deleteById(bookEntity);
    }

    private Author checkingAndSearchingAuthor(long authorId) {

        return authorRepository.findById(authorId)
                .orElseThrow(() -> new NotFoundException("Author with id %d not found".formatted(authorId)));
    }

    private List<Genre> checkingAndSearchingGenres(Set<Long> genresIds) {
        if (isEmpty(genresIds)) {
            throw new IllegalArgumentException("Genres ids must not be null");
        }

        var genres = genreRepository.findAllById(genresIds);
        if (isEmpty(genres) || genresIds.size() != genres.size()) {
            throw new NotFoundException("One or all genres with ids %s not found".formatted(genresIds));
        }

        return genres;
    }
}
