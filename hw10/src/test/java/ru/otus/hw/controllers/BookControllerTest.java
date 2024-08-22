package ru.otus.hw.controllers;

import com.google.gson.Gson;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.hw.dto.BookCreateDto;
import ru.otus.hw.dto.BookDto;
import ru.otus.hw.dto.BookUpdateDto;
import ru.otus.hw.exceptions.NotFoundException;
import ru.otus.hw.services.BookService;

import java.util.List;
import java.util.Optional;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("Контроллер книг ")
@WebMvcTest(BookController.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class BookControllerTest {

    private static final MediaType CONTENT_TYPE = new MediaType(APPLICATION_JSON, UTF_8);
    private static final long BOOK_ID_TEST = 1;
    private static final String BOOK_TITLE_TEST = "test";

    @Autowired
    private MockMvc mvc;

    @MockBean
    private BookService bookService;

    @DisplayName("должен вернуть список всех книг")
    @Order(1)
    @Test
    void getListBook() throws Exception {

        List<BookDto> bookDtoList = List.of(new BookDto());
        given(bookService.findAll()).willReturn(bookDtoList);

        Gson gson = new Gson();
        String resultJson = gson.toJson(bookDtoList);

        mvc.perform(get("/api/v1/book"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(content().json(resultJson));

    }

    @DisplayName("должен вернуть ошибку NOT_FOUND при поиске всех книг")
    @Order(2)
    @Test
    void getListBookException() throws Exception {

        given(bookService.findAll()).willThrow(NotFoundException.class);

        mvc.perform(get("/api/v1/book"))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(CONTENT_TYPE));

    }

    @DisplayName("должен вернуть книгу по ID")
    @Order(3)
    @Test
    void gteBookById() throws Exception {

        BookDto bookDto = new BookDto();
        bookDto.setId(BOOK_ID_TEST);
        bookDto.setTitle(BOOK_TITLE_TEST);
        given(bookService.findById(BOOK_ID_TEST)).willReturn(Optional.of(bookDto));

        Gson gson = new Gson();
        String resultJson = gson.toJson(bookDto);

        mvc.perform(get("/api/v1/book/" + BOOK_ID_TEST))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(content().json(resultJson));
    }

    @DisplayName("должен вернуть ошибку NOT_FOUND при поиске книги по ID")
    @Order(4)
    @Test
    void gteBookByIdException() throws Exception {

        given(bookService.findById(BOOK_ID_TEST)).willThrow(NotFoundException.class);

        mvc.perform(get("/api/v1/book/" + BOOK_ID_TEST))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(CONTENT_TYPE));
    }

    @DisplayName("должен создать книгу")
    @Order(5)
    @Test
    void createBook() throws Exception {

        BookCreateDto bookCreateDto = new BookCreateDto();
        bookCreateDto.setTitle(BOOK_TITLE_TEST);

        BookDto responseBookDto = new BookDto();
        responseBookDto.setId(BOOK_ID_TEST);
        responseBookDto.setTitle(BOOK_TITLE_TEST);

        given(bookService.create(bookCreateDto)).willReturn(responseBookDto);

        Gson gson = new Gson();
        String requestJson = gson.toJson(bookCreateDto);
        String responseJson = gson.toJson(responseBookDto);

        mvc.perform(post("/api/v1/book")
                        .contentType(APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(content().json(responseJson));
    }

    @DisplayName("должен вернуть ошибку INTERNAL_SERVER_ERROR при создание книги")
    @Order(6)
    @Test
    void createBookException() throws Exception {

        BookCreateDto bookCreateDto = new BookCreateDto();
        bookCreateDto.setTitle(BOOK_TITLE_TEST);

        given(bookService.create(bookCreateDto)).willThrow(RuntimeException.class);

        Gson gson = new Gson();
        String requestJson = gson.toJson(bookCreateDto);

        mvc.perform(post("/api/v1/book")
                        .contentType(APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isInternalServerError())
                .andExpect(content().contentType(CONTENT_TYPE));
    }

    @DisplayName("должен обновить книгу")
    @Order(7)
    @Test
    void updateBook() throws Exception {

        BookUpdateDto bookUpdateDto = new BookUpdateDto();
        bookUpdateDto.setId(BOOK_ID_TEST);
        bookUpdateDto.setTitle(BOOK_TITLE_TEST);

        BookDto responseBookDto = new BookDto();
        responseBookDto.setId(BOOK_ID_TEST);
        responseBookDto.setTitle(BOOK_TITLE_TEST);

        given(bookService.update(bookUpdateDto)).willReturn(responseBookDto);

        Gson gson = new Gson();
        String requestJson = gson.toJson(bookUpdateDto);
        String responseJson = gson.toJson(responseBookDto);

        mvc.perform(put("/api/v1/book")
                        .contentType(APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(content().json(responseJson));
    }

    @DisplayName("должен вернуть ошибку INTERNAL_SERVER_ERROR при обновление книги")
    @Order(8)
    @Test
    void updateBookException() throws Exception {

        BookUpdateDto bookUpdateDto = new BookUpdateDto();
        bookUpdateDto.setId(BOOK_ID_TEST);
        bookUpdateDto.setTitle(BOOK_TITLE_TEST);

        given(bookService.update(bookUpdateDto)).willThrow(RuntimeException.class);

        Gson gson = new Gson();
        String requestJson = gson.toJson(bookUpdateDto);

        mvc.perform(put("/api/v1/book")
                        .contentType(APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isInternalServerError())
                .andExpect(content().contentType(CONTENT_TYPE));
    }

    @DisplayName("должен удалить книгу по ID")
    @Order(9)
    @Test
    void deleteBook() throws Exception {

        mvc.perform(delete("/api/v1/book/" + BOOK_ID_TEST))
                .andExpect(status().isNoContent());
    }

    @DisplayName("должен вернуть ошибку INTERNAL_SERVER_ERROR при удаление книги")
    @Order(10)
    @Test
    void deleteBookException() throws Exception {

        doThrow(RuntimeException.class).when(bookService)
                .deleteById(BOOK_ID_TEST);

        mvc.perform(delete("/api/v1/book/" + BOOK_ID_TEST))
                .andExpect(status().isInternalServerError())
                .andExpect(content().contentType(CONTENT_TYPE));
    }
}