package ru.otus.hw.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.otus.hw.dto.order.OrderDto;
import ru.otus.hw.mappers.OrderMapper;

import static ru.otus.hw.dictionaries.Status.CONFIRMED;
import static ru.otus.hw.dictionaries.Status.CREATED;

@Slf4j
@RequiredArgsConstructor
@Service
public class ScheduledTasks {

    private final OrderService service;

    private final OrderMapper mapper;

    @Scheduled(fixedDelayString = "PT02S")
    public void creationBookBasedOnOrder() {
        log.info("Start ScheduledTasks");

        val orderList = service.findAllByStatus(CREATED);

        //todo: бизнес логика

        if (orderList.isEmpty()) {
            log.info("Completion ScheduledTasks -- orderList is empty");
            return;
        }

        for (OrderDto orderDto : orderList) {

            orderDto.setStatus(CONFIRMED);
            service.update(mapper.toUpdateDto(orderDto));
        }

        log.info("Completion ScheduledTasks");
    }

}
