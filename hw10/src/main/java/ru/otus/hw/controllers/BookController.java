package ru.otus.hw.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.otus.hw.dto.BookDto;
import ru.otus.hw.dto.BookCreateDto;
import ru.otus.hw.dto.BookUpdateDto;
import ru.otus.hw.services.BookService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @GetMapping("/api/v1/book")
    @ResponseStatus(HttpStatus.OK)
    public List<BookDto> getListBook() {
        return bookService.findAll();
    }

    @GetMapping("/api/v1/book/{bookId}")
    @ResponseStatus(HttpStatus.OK)
    public BookDto gteBookById(@PathVariable("bookId") long bookId) {
        return bookService.findById(bookId);
    }

    @PostMapping("/api/v1/book")
    @ResponseStatus(HttpStatus.CREATED)
    public BookDto createBook(@Valid @RequestBody BookCreateDto bookCreateDto) {
        return bookService.create(bookCreateDto);
    }

    @PutMapping("/api/v1/book")
    @ResponseStatus(HttpStatus.OK)
    public BookDto updateBook(@Valid @RequestBody BookUpdateDto bookUpdateDto) {
        return bookService.update(bookUpdateDto);
    }

    @DeleteMapping("/api/v1/book/{bookId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBook(@PathVariable("bookId") long bookId) {
        bookService.deleteById(bookId);
    }
}
