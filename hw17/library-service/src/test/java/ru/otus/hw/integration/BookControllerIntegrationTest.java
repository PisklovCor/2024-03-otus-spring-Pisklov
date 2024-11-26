package ru.otus.hw.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.hw.BaseIntegrationTest;
import ru.otus.hw.configuration.RestClientConfigurationTest;
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
@Import(RestClientConfigurationTest.class)
public class BookControllerIntegrationTest extends BaseIntegrationTest {

    private static final String BOOK_TITLE_TEST = "Book title test";

    private static final long BOOK_ID = 1;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    private WireMockServer mockServer;

    @BeforeEach
    void setUp() {
        mockServer = new WireMockServer(8888);
        mockServer.start();
    }

    @DisplayName("должен создать заказ на добавление книги")
    @Test
    void leaveBookOrder() throws Exception {

        mockServer.stubFor(WireMock.post(WireMock.urlEqualTo("/order-service/api/v1/order"))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.CREATED.value())
                        .withHeader(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                        .withBody(objectMapper.writeValueAsString(makeResponse()))));

        mvc.perform(post("/library-service/api/v1/book/order")
                        .content(BOOK_TITLE_TEST))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(makeResponse())));
    }

    @DisplayName("должен создать книгу у пользователя")
    @Test
    void takeBook() throws Exception {

        mockServer.stubFor(WireMock.post(WireMock.urlEqualTo("/account-service/api/v1/account/book"))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.CREATED.value())
                        .withHeader(CONTENT_TYPE, APPLICATION_JSON_VALUE)));

        mvc.perform(post("/library-service/api/v1/book/{bookId}/take", BOOK_ID))
                .andExpect(status().isCreated());
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
