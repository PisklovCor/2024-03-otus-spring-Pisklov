package ru.otus.hw.controllers;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.hw.dto.AuthorDto;
import ru.otus.hw.dto.BookDto;
import ru.otus.hw.dto.GenreDto;
import ru.otus.hw.services.AuthorService;
import ru.otus.hw.services.BookService;
import ru.otus.hw.services.GenreService;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DisplayName("Контроллер для работы с книгами ")
@WebMvcTest(BookController.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class BookControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private BookService bookService;

    @MockBean
    private AuthorService authorService;

    @MockBean
    private GenreService genreService;

    private static final BookDto mockBook = new BookDto();
    private static final List<BookDto> mockBookList = new ArrayList<>();
    private static final List<AuthorDto> mockAuthorsList = new ArrayList<>();
    private static final List<GenreDto> mockGenresList = new ArrayList<>();

    @BeforeAll
    static void init() {

        AuthorDto authorDto = new AuthorDto(1, "Author_1");
        mockAuthorsList.add(authorDto);
        GenreDto genreDto = new GenreDto(1, "Genre_1");
        mockGenresList.add(genreDto);

        mockBook.setTitle("BookTitle_1");
        mockBook.setAuthor(authorDto);
        mockBook.setGenres(mockGenresList);

        mockBookList.add(mockBook);
    }

    @Order(1)
    @DisplayName("должен вернуть страницу списка книг")
    @Test
    void listBook() throws Exception {
        given(bookService.findAll())
                .willReturn(mockBookList);

        this.mvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("list"))
                .andExpect(model().attribute("books", mockBookList));
    }

    @Order(2)
    @DisplayName("должен вернуть страницу создания новой книги")
    @Test
    void createBookPage() throws Exception {

        given(bookService.findById(1))
                .willReturn(Optional.of(mockBook));
        given(authorService.findAll())
                .willReturn(mockAuthorsList);
        given(genreService.findAll())
                .willReturn(mockGenresList);

        this.mvc.perform(get("/book/create"))
                .andExpect(status().isOk())
                .andExpect(view().name("create"))
                .andExpect(model().attribute("authors", mockAuthorsList))
                .andExpect(model().attribute("genres", mockGenresList));
    }

    @Order(3)
    @DisplayName("должен вернуть начальную страницу, после создания книги")
    @Test
    void createBook() throws Exception {

        this.mvc.perform(post("/book/create"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/"));
    }

    @Order(4)
    @DisplayName("должен вернуть страницу редактирования книги")
    @Test
    void editBook() throws Exception {

        given(bookService.findById(1))
                .willReturn(Optional.of(mockBook));
        given(authorService.findAll())
                .willReturn(mockAuthorsList);
        given(genreService.findAll())
                .willReturn(mockGenresList);

        this.mvc.perform(get("/book/edit")
                        .param("id", "1"))
                .andExpect(status().isOk())
                .andExpect(view().name("edit"))
                .andExpect(model().attribute("book", mockBook))
                .andExpect(model().attribute("authors", mockAuthorsList))
                .andExpect(model().attribute("genres", mockGenresList));
    }

    @Order(5)
    @DisplayName("должен вернуть начальную страницу, после сохранения книги")
    @Test
    void saveBook() throws Exception {

        this.mvc.perform(post("/book/save"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/"));
    }

    @Order(6)
    @DisplayName("должен вернуть начальную страницу, после удаления книги")
    @Test
    void deleteBook() throws Exception {

        this.mvc.perform(post("/book/delete")
                        .param("id", "1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/"));
    }
}