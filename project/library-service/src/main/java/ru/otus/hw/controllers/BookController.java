package ru.otus.hw.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.otus.hw.dto.library.BookDto;
import ru.otus.hw.dto.library.BookCreateDto;
import ru.otus.hw.dto.library.BookUpdateDto;
import ru.otus.hw.dto.account.AccountBookDto;
import ru.otus.hw.dto.order.OrderDto;
import ru.otus.hw.services.BookService;

import java.util.List;

@Tag(name = "Контроллер книг", description = "Контроллер взаимодействия с книга")
@RestController
@RequestMapping("/library-service")
@RequiredArgsConstructor
public class BookController {

    private final BookService service;

    @Operation(
            summary = "Получение книг",
            description = "Позволяет получить список всех существующих книг"
    )
    @GetMapping("/api/v1/book")
    @ResponseStatus(HttpStatus.OK)
    public List<BookDto> getListBook() {
        return service.findAll();
    }

    @Operation(
            summary = "Получение книги по ID",
            description = "Позволяет получить книгу по его ID"
    )
    @GetMapping("/api/v1/book/{bookId}")
    @ResponseStatus(HttpStatus.OK)
    public BookDto gteBookById(@PathVariable("bookId")
                               @Parameter(description = "ID книги", example = "1") long bookId) {
        return service.findById(bookId);
    }

    @Operation(
            summary = "Создание книги",
            description = "Позволяет создать новую книгу"
    )
    @PostMapping("/api/v1/book")
    @ResponseStatus(HttpStatus.CREATED)
    public BookDto createBook(@Valid @RequestBody BookCreateDto bookCreateDto) {
        return service.create(bookCreateDto);
    }

    @Operation(
            summary = "Обновление книги",
            description = "Позволяет обновить существующую книгу"
    )
    @PutMapping("/api/v1/book")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public BookDto updateBook(@Valid @RequestBody BookUpdateDto bookUpdateDto) {
        return service.update(bookUpdateDto);
    }

    @Operation(
            summary = "Удаление книги",
            description = "Позволяет удалить книгу по ID"
    )
    @DeleteMapping("/api/v1/book/{bookId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBook(@PathVariable("bookId")
                           @Parameter(description = "ID книги", example = "1") long bookId) {
        service.deleteById(bookId);
    }

    @Operation(
            summary = "Разместить заказ",
            description = "Позволяет разместить заказ на пополнение библиотеки книгой по названию"
    )
    @PostMapping("/api/v1/book/order")
    @ResponseStatus(HttpStatus.CREATED)
    public OrderDto leaveBookOrder(@RequestBody String bookTitle) {
        return service.leaveBookOrder(bookTitle);
    }

    @Operation(
            summary = "Взять книгу",
            description = "Позволяет пользователю получить книгу"
    )
    @PostMapping("/api/v1/book/{bookId}/take")
    @ResponseStatus(HttpStatus.CREATED)
    public AccountBookDto takeBook(@PathVariable("bookId")
                         @Parameter(description = "ID книги", example = "1") long bookId) {
        return service.takeBook(bookId);
    }
}
