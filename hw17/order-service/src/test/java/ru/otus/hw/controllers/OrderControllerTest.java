package ru.otus.hw.controllers;

import com.google.gson.Gson;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.hw.SpringBootApplicationTest;
import ru.otus.hw.dto.order.OrderCreateDto;
import ru.otus.hw.dto.order.OrderDto;
import ru.otus.hw.dto.order.OrderUpdateDto;
import ru.otus.hw.services.OrderService;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.otus.hw.dictionaries.Status.*;

@DisplayName("Контроллер заказов ")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class OrderControllerTest extends SpringBootApplicationTest {

    private static final String USER_LOGIN = "guest";
    private static final String INSERT_TITLE_VALUE = "BookTitle_4";
    private static final long FIRST_ORDER_ID = 1L;
    private static final String UPDATE_TITLE_VALUE = "BookTitle_5";
    private static final long ORDER_ID_TEST = 1L;

    @Autowired
    private MockMvc mvc;

    @MockBean
    private OrderService service;

    private Gson gson;

    @BeforeEach
    void setUp() {
        gson = new Gson();
    }

    @DisplayName("должен вернуть список всех заказов")
    @Order(1)
    @Test
    void getListOrder() throws Exception {

        List<OrderDto> bookDtoList = List.of(new OrderDto());
        given(service.findAll()).willReturn(bookDtoList);

        mvc.perform(get("/api/v1/order"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(content().json(gson.toJson(bookDtoList)));

    }

    @DisplayName("должен вернуть заказ по логину пользователя")
    @Order(2)
    @Test
    void gteListOrderByLogin() throws Exception {

        OrderDto dto = new OrderDto();
        dto.setId(ORDER_ID_TEST);
        dto.setLogin(USER_LOGIN);
        dto.setBookTitle(INSERT_TITLE_VALUE);
        given(service.findAllByLogin(USER_LOGIN)).willReturn(List.of(dto));

        mvc.perform(get("/api/v1/order/" + USER_LOGIN))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(content().json(gson.toJson(List.of(dto))));
    }

    @DisplayName("должен создать заказ")
    @Order(3)
    @Test
    void createOrder() throws Exception {

        OrderCreateDto orderCreateDto = new OrderCreateDto(USER_LOGIN, INSERT_TITLE_VALUE);

        OrderDto responseBookDto = new OrderDto();
        responseBookDto.setId(ORDER_ID_TEST);
        responseBookDto.setLogin(USER_LOGIN);
        responseBookDto.setBookTitle(INSERT_TITLE_VALUE);
        responseBookDto.setStatus(CREATED);

        given(service.create(orderCreateDto)).willReturn(responseBookDto);

        mvc.perform(post("/api/v1/order")
                        .contentType(APPLICATION_JSON)
                        .content(gson.toJson(orderCreateDto)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(content().json(gson.toJson(responseBookDto)));

    }

    @DisplayName("должен обновить заказ")
    @Order(4)
    @Test
    void updateOrder() throws Exception {

        OrderUpdateDto orderUpdateDto = new OrderUpdateDto(FIRST_ORDER_ID, USER_LOGIN,
                UPDATE_TITLE_VALUE, WAIT);

        OrderDto responseOrderDto = new OrderDto();
        responseOrderDto.setId(FIRST_ORDER_ID);
        responseOrderDto.setLogin(USER_LOGIN);
        responseOrderDto.setBookTitle(UPDATE_TITLE_VALUE);
        responseOrderDto.setStatus(WAIT);

        given(service.update(orderUpdateDto)).willReturn(responseOrderDto);

        mvc.perform(put("/api/v1/order")
                        .contentType(APPLICATION_JSON)
                        .content(gson.toJson(orderUpdateDto)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(content().json(gson.toJson(responseOrderDto)));

    }
}