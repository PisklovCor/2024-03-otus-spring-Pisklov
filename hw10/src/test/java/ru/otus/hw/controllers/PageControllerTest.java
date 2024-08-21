package ru.otus.hw.controllers;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DisplayName("Контроллер со страницами ")
@WebMvcTest(PageController.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PageControllerTest {

    @Autowired
    private MockMvc mvc;

    @Order(1)
    @DisplayName("должен вернуть страницу списка книг")
    @Test
    void listBook() throws Exception {

        mvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("list"));
    }

    @Order(2)
    @DisplayName("должен вернуть страницу создания новой книги")
    @Test
    void createBookPage() throws Exception {
        mvc.perform(get("/book/create"))
                .andExpect(status().isOk())
                .andExpect(view().name("create"));
    }

    @Order(4)
    @DisplayName("должен вернуть страницу редактирования книги")
    @Test
    void editBook() throws Exception {
        mvc.perform(get("/book/edit")
                        .param("id", "1"))
                .andExpect(status().isOk())
                .andExpect(view().name("edit"));
    }
}