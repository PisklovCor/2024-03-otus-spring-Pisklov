package ru.otus.hw.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
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
import ru.otus.hw.dto.order.OrderDto;
import ru.otus.hw.services.BookService;

import java.util.List;

@Tag(name = "Контроллер книг", description = "Контроллер взаимодействия с книга")
@RestController
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @Operation(
            summary = "Получение книг",
            description = "Позволяет получить список всех существующих книг"
    )
    @GetMapping("/api/v1/book")
    @ResponseStatus(HttpStatus.OK)
    public List<BookDto> getListBook() {
        return bookService.findAll();
    }

    @Operation(
            summary = "Получение книги по ID",
            description = "Позволяет получить книгу по его ID"
    )
    @GetMapping("/api/v1/book/{bookId}")
    @ResponseStatus(HttpStatus.OK)
    public BookDto gteBookById(@PathVariable("bookId")
                               @Parameter(description = "ID книги", example = "1") long bookId) {
        return bookService.findById(bookId);
    }

    @Operation(
            summary = "Создание книги",
            description = "Позволяет создать новую книгу"
    )
    @PostMapping("/api/v1/book")
    @ResponseStatus(HttpStatus.CREATED)
    public BookDto createBook(@Valid @RequestBody BookCreateDto bookCreateDto) {
        return bookService.create(bookCreateDto);
    }

    @Operation(
            summary = "Обновление книги",
            description = "Позволяет обновить существующую книгу"
    )
    @PutMapping("/api/v1/book")
    @ResponseStatus(HttpStatus.OK)
    public BookDto updateBook(@Valid @RequestBody BookUpdateDto bookUpdateDto) {
        return bookService.update(bookUpdateDto);
    }

    @Operation(
            summary = "Удаление книги",
            description = "Позволяет удалить книгу по ID"
    )
    @DeleteMapping("/api/v1/book/{bookId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBook(@PathVariable("bookId")
                           @Parameter(description = "ID книги", example = "1") long bookId) {
        bookService.deleteById(bookId);
    }

    @Operation(
            summary = "Разместить заказ",
            description = "Позволяет разместить заказ на пополнение библиотеки книгой по названию"
    )
    @PostMapping("/api/v1/book/order")
    @ResponseStatus(HttpStatus.CREATED)
    public OrderDto leaveBookOrder(@RequestBody String bookTitle) {
        return bookService.leaveBookOrder(bookTitle);
    }
}
