package ru.otus.hw.integration;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.google.gson.Gson;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.hw.BaseIntegrationTest;
import ru.otus.hw.dto.order.OrderDto;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.common.ContentTypes.APPLICATION_JSON;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static ru.otus.hw.dictionaries.Status.CREATED;

@DisplayName("Контроллер книг: Интеграционный тест ")
public class BookControllerIntegrationTest extends BaseIntegrationTest {

    private static final String BOOK_TITLE_TEST = "Book title test";

    @Autowired
    private MockMvc mvc;

    private WireMockServer mockServer;

    private Gson gson;

    @BeforeEach
    void setUp() {
        mockServer = new WireMockServer(8001);
        mockServer.start();
        gson = new Gson();
    }

    @DisplayName("должен создать заказ на добавление книги")
    @Test
    void getListBook() throws Exception {

        mockServer.stubFor(WireMock.post(WireMock.urlEqualTo("/api/v1/order"))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.CREATED.value())
                        .withHeader(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                        .withBody(
                                gson.toJson(makeResponse()))));

        mvc.perform(post("/api/v1/book/order")
                        .content(BOOK_TITLE_TEST))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(content().json(gson.toJson(makeResponse())));
    }

    @AfterEach
    void cleanUp() {
        mockServer.stop();
    }

    private OrderDto makeResponse() {
        var order = new OrderDto();
        order.setId(1);
        order.setLogin("guest");
        order.setBookTitle(BOOK_TITLE_TEST);
        order.setStatus(CREATED);
        return order;
    }
}
