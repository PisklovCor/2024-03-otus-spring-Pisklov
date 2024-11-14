package ru.otus.hw.services;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw.clients.AccountClient;
import ru.otus.hw.clients.OrderClient;
import ru.otus.hw.dto.BookUpdateDto;
import ru.otus.hw.dto.account.AccountBookCreateDto;
import ru.otus.hw.dto.order.OrderCreateDto;
import ru.otus.hw.dto.order.OrderDto;
import ru.otus.hw.mappers.BookMapper;
import ru.otus.hw.dto.BookCreateDto;
import ru.otus.hw.dto.BookDto;
import ru.otus.hw.exceptions.NotFoundException;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Genre;
import ru.otus.hw.repositories.AuthorRepository;
import ru.otus.hw.repositories.BookRepository;
import ru.otus.hw.repositories.GenreRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.springframework.util.CollectionUtils.isEmpty;

@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookService {

    private final BookMapper bookMapper;

    private final AuthorRepository authorRepository;

    private final GenreRepository genreRepository;

    private final BookRepository bookRepository;

    private final OrderClient orderClient;

    private final AccountClient accountClient;

    @Override
    @Transactional(readOnly = true)
    public List<BookDto> findAll() {
        return bookRepository.findAll().stream().map(bookMapper::toDto).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public BookDto findById(long id) {
        return bookRepository.findById(id).stream()
                .map(bookMapper::toDto)
                .findAny()
                .orElseThrow(() -> new NotFoundException("Book with id %d not found".formatted(id)));
    }

    @Override
    @Transactional
    public BookDto create(BookCreateDto dto) {
        return bookMapper.toDto(bookRepository.save(
                new Book(null, dto.getTitle(), checkingAndSearchingAuthor(dto.getAuthorId()),
                        checkingAndSearchingGenres(new HashSet<>(dto.getGenresId())))));
    }

    @Override
    @Transactional
    public BookDto update(BookUpdateDto dto) {

        final long bookId = dto.getId();

        var book = bookRepository.findById(bookId)
                .orElseThrow(() -> new NotFoundException("Book with id %d not found".formatted(bookId)));

        book.setTitle(dto.getTitle());
        book.setAuthor(checkingAndSearchingAuthor(dto.getAuthorId()));
        book.setGenres(checkingAndSearchingGenres(new HashSet<>(dto.getGenresId())));

        return bookMapper.toDto(bookRepository.save(book));
    }

    @Override
    @Transactional
    public void deleteById(long id) {
        bookRepository.deleteById(id);
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

    //todo: переделать на фасад
    @Override
    public OrderDto leaveBookOrder(String bookTitle) {

        //todo: планируется вытягивать из токена
        val login = "user";

        return orderClient.createOrder(new OrderCreateDto(login, bookTitle));

        //todo: евент успеха или ошибки
    }

    //todo: переделать на фасад
    @Override
    @Transactional(readOnly = true)
    public void takeBook(long bookId) {

        BookDto bookDto = bookRepository.findById(bookId).stream()
                .map(bookMapper::toDto)
                .findAny()
                .orElseThrow(() -> new NotFoundException("Book with id %d not found".formatted(bookId)));

        //todo: планируется вытягивать из токена
        val login = "user";

        accountClient.takeBook(new AccountBookCreateDto(login, bookDto.getId()));

        //todo: евент успеха
    }
}
