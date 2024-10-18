package ru.otus.hw.controllers;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.hw.SpringBootApplicationTest;
import ru.otus.hw.dto.*;
import ru.otus.hw.exceptions.NotFoundException;
import ru.otus.hw.services.AuthorService;
import ru.otus.hw.services.BookService;
import ru.otus.hw.services.GenreService;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DisplayName("Контроллер для работы с книгами ")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class BookControllerTest extends SpringBootApplicationTest {

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
    @DisplayName("должен вернуть начальную страницу для всех пользоваетелей")
    @Test
    void indexPage() throws Exception {

        mvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"));
    }

    @WithMockUser(
            username = "admin",
            authorities = {"ROLE_ADMIN"}
    )
    @Order(2)
    @DisplayName("должен вернуть страницу списка книг")
    @Test
    void listBook() throws Exception {
        given(bookService.findAll())
                .willReturn(mockBookList);

        mvc.perform(get("/book"))
                .andExpect(status().isOk())
                .andExpect(view().name("list"))
                .andExpect(model().attribute("books", mockBookList));
    }

    @WithMockUser(
            username = "admin",
            authorities = {"ROLE_ADMIN"}
    )
    @Order(3)
    @DisplayName("должен вернуть страницу создания новой книги")
    @Test
    void createBookPage() throws Exception {

        given(bookService.findById(1))
                .willReturn(Optional.of(mockBook));
        given(authorService.findAll())
                .willReturn(mockAuthorsList);
        given(genreService.findAll())
                .willReturn(mockGenresList);

        mvc.perform(get("/book/create"))
                .andExpect(status().isOk())
                .andExpect(view().name("create"))
                .andExpect(model().attribute("authors", mockAuthorsList))
                .andExpect(model().attribute("genres", mockGenresList));
    }

    @WithMockUser(
            username = "admin",
            authorities = {"ROLE_ADMIN"}
    )
    @Order(4)
    @DisplayName("должен вернуть начальную страницу, после создания книги")
    @Test
    void createBook() throws Exception {

        var book = bildBookDto();

        mvc.perform(post("/book/create")
                .param("id", String.valueOf(book.getId()))
                .param("title", book.getTitle()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/book"));
    }

    @WithMockUser(
            username = "admin",
            authorities = {"ROLE_ADMIN"}
    )
    @Order(5)
    @DisplayName("должен вернуть страницу редактирования книги")
    @Test
    void editBook() throws Exception {

        given(bookService.findById(1))
                .willReturn(Optional.of(mockBook));
        given(authorService.findAll())
                .willReturn(mockAuthorsList);
        given(genreService.findAll())
                .willReturn(mockGenresList);

        mvc.perform(get("/book/edit")
                        .param("id", "1"))
                .andExpect(status().isOk())
                .andExpect(view().name("edit"))
                .andExpect(model().attribute("book", mockBook))
                .andExpect(model().attribute("authors", mockAuthorsList))
                .andExpect(model().attribute("genres", mockGenresList));
    }

    @WithMockUser(
            username = "admin",
            authorities = {"ROLE_ADMIN"}
    )
    @Order(6)
    @DisplayName("должен вернуть начальную страницу, после сохранения книги")
    @Test
    void saveBook() throws Exception {

        var book = bildBookDto();

        mvc.perform(post("/book/save")
                .param("id", String.valueOf(book.getId()))
                .param("title", book.getTitle()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/book"));
    }

    @WithMockUser(
            username = "admin",
            authorities = {"ROLE_ADMIN"}
    )
    @Order(7)
    @DisplayName("должен вернуть ошибку 404 при сохранение")
    @Test
    void saveBookNotFound() throws Exception {

        when(bookService.update(any())).thenThrow(NotFoundException.class);

        mvc.perform(post("/book/save")
                        .param("id", "-1")
                        .param("title", "test error"))
                .andExpect(status().isOk())
                .andExpect(view().name("error/404"));
    }

    @WithMockUser(
            username = "admin",
            authorities = {"ROLE_ADMIN"}
    )
    @Order(8)
    @DisplayName("должен вернуть начальную страницу, после удаления книги")
    @Test
    void deleteBook() throws Exception {

        mvc.perform(post("/book/delete")
                        .param("id", "1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/book"));
    }

    @WithMockUser(
            username = "admin",
            authorities = {"ROLE_ADMIN"}
    )
    @Order(9)
    @DisplayName("должен вернуть ошибку 404 при удаление")
    @Test
    void deleteBookNotFound() throws Exception {

        doThrow(NotFoundException.class).when(bookService)
                .deleteById(-1);

        mvc.perform(post("/book/delete")
                        .param("id", "-1"))
                .andExpect(status().isOk())
                .andExpect(view().name("error/404"));
    }

    private BookDto bildBookDto() {
        AuthorDto authorDto = new AuthorDto();
        authorDto.setId(1);

        GenreDto genreDto = new GenreDto();
        genreDto.setId(1);

        BookDto bookDto = new BookDto();
        bookDto.setId(1L);
        bookDto.setTitle("BookTitle_1");
        bookDto.setAuthor(authorDto);
        bookDto.setGenres(List.of(genreDto));

        return bookDto;
    }
}