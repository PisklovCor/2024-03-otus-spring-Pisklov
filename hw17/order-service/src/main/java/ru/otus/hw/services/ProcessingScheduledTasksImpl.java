package ru.otus.hw.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.otus.hw.clients.LibraryClient;
import ru.otus.hw.dto.BookCreateDto;
import ru.otus.hw.dto.order.OrderDto;
import ru.otus.hw.mappers.OrderMapper;
import ru.otus.hw.utils.CacheService;

import java.util.List;

import static ru.otus.hw.dictionaries.Status.CREATED;
import static ru.otus.hw.dictionaries.Status.CONFIRMED;
import static ru.otus.hw.dictionaries.Status.ERROR;

@Slf4j
@RequiredArgsConstructor
@Service
public class ProcessingScheduledTasksImpl implements ScheduledTasks {

    private final OrderService service;

    private final OrderMapper mapper;

    private final LibraryClient client;

    private final OrderFacade facade;


    @Scheduled(fixedDelayString = "PT010S")
    @Override
    public void run() {
        log.info("Start ProcessingScheduledTasksImpl");
        creationBookBasedOnOrder();
        log.info("Completion ProcessingScheduledTasksImpl");

    }

    private void creationBookBasedOnOrder() {

        val orderList = service.findAllByStatus(CREATED);

        if (orderList.isEmpty()) {
            log.info("Completion ProcessingScheduledTasksImpl -- orderList is empty");
            return;
        }

        for (OrderDto orderDto : orderList) {
            try {
                createBook(orderDto.getBookTitle());
                orderDto.setStatus(CONFIRMED);
                facade.updateAndSendMessage(mapper.toUpdateDto(orderDto));

            } catch (Exception e) {
                log.error(e.getMessage());
                orderDto.setStatus(ERROR);
                //TODO: переделать на фасад ошибки
                service.update(mapper.toUpdateDto(orderDto));
            }
        }
    }

    private void createBook(String bookTitle) {
        BookCreateDto bookDto = new BookCreateDto();
        bookDto.setTitle(bookTitle);
        bookDto.setAuthorId(CacheService.getRandomElementAuthorsCache());
        bookDto.setGenresId(List.of(CacheService.getRandomElementGenresCache()));

        var book = client.createBook(bookDto);
        log.info("Create book=[{}]", book);
    }
}
