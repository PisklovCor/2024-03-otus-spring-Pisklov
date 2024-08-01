package ru.otus.hw.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.otus.hw.dto.AuthorDto;
import ru.otus.hw.dto.BookCreateDto;
import ru.otus.hw.dto.BookUpdateDto;
import ru.otus.hw.dto.BookDto;
import ru.otus.hw.dto.GenreDto;
import ru.otus.hw.exceptions.NotFoundException;
import ru.otus.hw.services.AuthorService;
import ru.otus.hw.services.BookService;
import ru.otus.hw.services.GenreService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    private final AuthorService authorService;

    private final GenreService genreService;

    @GetMapping("/")
    public String listBook(Model model) {
        List<BookDto> books = bookService.findAll();
        model.addAttribute("books", books);
        return "list";
    }

    @GetMapping("/book/create")
    public String createBook(Model model) {
        List<AuthorDto> author = authorService.findAll();
        List<GenreDto> genre = genreService.findAll();

        GenreDto genreDtoOne = new GenreDto();
        GenreDto genreDtoTwo = new GenreDto();

        BookCreateDto book = new BookCreateDto();
        book.setGenres(List.of(genreDtoOne, genreDtoTwo));
        model.addAttribute("book", book);
        model.addAttribute("authors", author);
        model.addAttribute("genres", genre);
        return "create";
    }

    @PostMapping("/book/create")
    public String createBook(@Valid BookCreateDto book) {
        bookService.create(book);
        return "redirect:/";
    }

    @GetMapping("/book/edit")
    public String editBook(@RequestParam("id") long id, Model model) {

        BookDto book = bookService.findById(id).orElseThrow(NotFoundException::new);
        List<AuthorDto> author = authorService.findAll();
        List<GenreDto> genre = genreService.findAll();

        model.addAttribute("book", book);
        model.addAttribute("authors", author);
        model.addAttribute("genres", genre);
        return "edit";
    }

    @PostMapping("/book/save")
    public String saveBook(@Valid BookUpdateDto book) {
        bookService.update(book);
        return "redirect:/";
    }

    @PostMapping("/book/delete")
    public String deleteBook(@RequestParam("id") long id) {
        bookService.deleteById(id);
        return "redirect:/";
    }
}
