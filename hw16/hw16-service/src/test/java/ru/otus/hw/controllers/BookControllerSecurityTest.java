package ru.otus.hw.controllers;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.hw.SpringBootApplicationTest;
import ru.otus.hw.dto.AuthorDto;
import ru.otus.hw.dto.BookDto;
import ru.otus.hw.dto.GenreDto;
import ru.otus.hw.services.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DisplayName("Защищенный контроллер для работы с книгами ")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class BookControllerSecurityTest extends SpringBootApplicationTest {

    private static final String USER_TEST_LOGIN = "user_test";
    private static final String USER_TEST_PASSWORD = "$2a$12$2tcN8kkCE7dcxyXNB9nTV.hKgNPBXIcHfplo0nynixi2BqVsseX1q";
    private static final String USER_TEST_ROLE = "USER";

    @Autowired
    private MockMvc mvc;

    @MockBean
    private BookService bookService;

    @MockBean
    private AuthorService authorService;

    @MockBean
    private GenreService genreService;

    private static UserDetails userDetails;

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

        userDetails = User.builder()
                .username(USER_TEST_LOGIN)
                .password(USER_TEST_PASSWORD)
                .roles(USER_TEST_ROLE)
                .build();
    }

    @Order(1)
    @DisplayName("должен вернуть начальную страницу для всех пользоваетелей")
    @Test
    void indexPage() throws Exception {

        mvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"));

        mvc.perform(get("/").with(user(userDetails)))
                .andExpect(status().isOk())
                .andExpect(view().name("index"));
    }

    @Order(2)
    @DisplayName("должен вернуть страницу списка книг для аутентифицированного пользователя")
    @Test
    void listBook() throws Exception {

        mvc.perform(get("/book").with(user(userDetails)))
                .andExpect(status().isOk())
                .andExpect(view().name("list"));
    }

    @Order(3)
    @DisplayName("(список книг) должен вернуть страницу входа для не аутентифицированного пользователя")
    @Test
    void listBookWithoutAuthentication() throws Exception {

        mvc.perform(get("/book"))
                .andExpect(status().is3xxRedirection());
    }

    @Order(4)
    @DisplayName("должен вернуть страницу создания новой книги для аутентифицированного пользователя")
    @Test
    void createBookPage() throws Exception {

        mvc.perform(get("/book/create").with(user(userDetails)))
                .andExpect(status().isOk())
                .andExpect(view().name("create"));
    }

    @Order(5)
    @DisplayName("(создание книги) должен вернуть страницу входа для не аутентифицированного пользователя")
    @Test
    void createBookPageWithoutAuthentication() throws Exception {

        mvc.perform(get("/book/create"))
                .andExpect(status().is3xxRedirection());
    }

    @Order(6)
    @DisplayName("должен вернуть страницу редактирования книги для аутентифицированного пользователя")
    @Test
    void editBook() throws Exception {

        given(bookService.findById(1))
                .willReturn(Optional.of(mockBook));
        given(authorService.findAll())
                .willReturn(mockAuthorsList);
        given(genreService.findAll())
                .willReturn(mockGenresList);

        mvc.perform(get("/book/edit").with(user(userDetails))
                        .param("id", "1"))
                .andExpect(status().isOk())
                .andExpect(view().name("edit"));
    }

    @Order(7)
    @DisplayName("(редактирование книги )должен вернуть страницу входа для не аутентифицированного пользователя")
    @Test
    void editBookWithoutAuthentication() throws Exception {

        given(bookService.findById(1))
                .willReturn(Optional.of(mockBook));
        given(authorService.findAll())
                .willReturn(mockAuthorsList);
        given(genreService.findAll())
                .willReturn(mockGenresList);

        mvc.perform(get("/book/edit")
                        .param("id", "1"))
                .andExpect(status().is3xxRedirection());
    }
}