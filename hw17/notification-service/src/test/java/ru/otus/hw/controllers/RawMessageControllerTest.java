package ru.otus.hw.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.hw.SpringBootApplicationTest;
import ru.otus.hw.dto.notification.RawMessageCreateDto;
import ru.otus.hw.services.RawMessageService;

import static org.hamcrest.core.StringContains.containsString;
import static org.springframework.http.MediaType.APPLICATION_JSON;

import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_CLASS;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.otus.hw.dictionaries.ExternalSystem.ORDER_SERVICE;
import static ru.otus.hw.dictionaries.MessageType.CREATION;

@DisplayName("Контроллер сырых сообщений ")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
class RawMessageControllerTest extends SpringBootApplicationTest {

    private static final String USER_LOGIN_TEST = "user_test";

    private static final String CONTENT_TEST = "content";

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private RawMessageService service;

    @DisplayName("должен вернуть список всех заказов")
    @Sql(scripts = {"/sql/raw_message_insert.sql"}, executionPhase = BEFORE_TEST_METHOD)
    @Test
    void getListRawMessage() throws Exception {

        var resultSet = service.findAll();

        mvc.perform(get("/notification-service/api/v1/raw-message"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(resultSet)));
    }

    @DisplayName("должен создать сырое сообщение")
    @Sql(scripts = {"/sql/raw_message_delete.sql"}, executionPhase = AFTER_TEST_METHOD)
    @Test
    void createRawMessage() throws Exception {
        val rawMessageCreateDto = RawMessageCreateDto.builder()
                .login(USER_LOGIN_TEST)
                .content(CONTENT_TEST)
                .externalSystemName(ORDER_SERVICE)
                .messageType(CREATION)
                .build();

        mvc.perform(post("/notification-service/api/v1/raw-message")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(rawMessageCreateDto)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(content().string(containsString(USER_LOGIN_TEST)))
                .andExpect(content().string(containsString(CONTENT_TEST)));
    }
}
