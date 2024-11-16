package ru.otus.hw.services;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import ru.otus.hw.SpringBootApplicationTest;
import ru.otus.hw.dto.order.OrderCreateDto;
import ru.otus.hw.dto.order.OrderUpdateDto;
import ru.otus.hw.repositories.OrderRepository;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static ru.otus.hw.dictionaries.Status.CONFIRMED;
import static ru.otus.hw.dictionaries.Status.CREATED;
import static ru.otus.hw.dictionaries.Status.WAIT;

@DisplayName("Сервис для работы с заказакми ")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class OrderServiceImplTest extends SpringBootApplicationTest {

    private static final int EXPECTED_NUMBER_OF_ORDER = 3;
    private static final String USER_LOGIN = "guest";
    private static final String TEST_USER_LOGIN = "user_test";
    private static final int EXPECTED_NUMBER_OF_ORDER_BY_USER = 2;
    private static final String INSERT_TITLE_VALUE = "BookTitle_4";
    private static final long FIRST_ORDER_ID = 1L;
    private static final String UPDATE_TITLE_VALUE = "BookTitle_5";

    @Autowired
    private OrderService service;

    @Autowired
    private OrderRepository repository;

    @DisplayName("должен загружать список всех заказов")
    @Test
    @Order(1)
    void findAll() {
        var listOrderDto = service.findAll();

        assertThat(listOrderDto).isNotNull().hasSize(EXPECTED_NUMBER_OF_ORDER)
                .allMatch(o -> !o.getLogin().isEmpty())
                .allMatch(o -> o.getStatus() != null);

    }

    @DisplayName("должен загружать информацию о нужных заказ по логину пользователя")
    @Test
    @Order(2)
    void findAllByLogin() {
        var listOrderDto = service.findAllByLogin(USER_LOGIN);

        assertThat(listOrderDto).isNotNull().hasSize(EXPECTED_NUMBER_OF_ORDER_BY_USER)
                .allMatch(o -> !o.getLogin().isEmpty())
                .allMatch(o -> o.getStatus() != null);

    }

    @DisplayName("должен загружать информацию о нужных заказ по статусу")
    @Test
    @Order(3)
    void findAllByStatus() {
        var listOrderDto = service.findAllByStatus(WAIT);

        assertThat(listOrderDto).isNotNull().hasSize(EXPECTED_NUMBER_OF_ORDER)
                .allMatch(o -> !o.getLogin().isEmpty())
                .allMatch(o -> o.getStatus() != null);

    }

    @DisplayName("должен создать заказ с полной информацией")
    @Test
    @Order(4)
    void create() {
        var newOrderDto = service.create(new OrderCreateDto(TEST_USER_LOGIN, INSERT_TITLE_VALUE));
        var optionalExpectedOrderEntity = repository.findById(newOrderDto.getId());

        assertTrue(optionalExpectedOrderEntity.isPresent());
        assertEquals(INSERT_TITLE_VALUE, optionalExpectedOrderEntity.get().getBookTitle());
        assertEquals(TEST_USER_LOGIN, optionalExpectedOrderEntity.get().getLogin());
        assertEquals(CREATED, optionalExpectedOrderEntity.get().getStatus());
    }

    @DisplayName("должен обновить заказ с полной информацией")
    @Test
    @Order(5)
    void update() {
        var updateOrderDto = service.update(new OrderUpdateDto(FIRST_ORDER_ID, TEST_USER_LOGIN,
                UPDATE_TITLE_VALUE, CONFIRMED));
        var optionalExpectedOrderEntity = repository.findById(updateOrderDto.getId());

        assertTrue(optionalExpectedOrderEntity.isPresent());
        assertEquals(UPDATE_TITLE_VALUE, optionalExpectedOrderEntity.get().getBookTitle());
        assertEquals(TEST_USER_LOGIN, optionalExpectedOrderEntity.get().getLogin());
        assertEquals(CONFIRMED, optionalExpectedOrderEntity.get().getStatus());
    }
}