package ru.otus.hw.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.stereotype.Service;
import ru.otus.hw.clients.NotificationClient;
import ru.otus.hw.clients.OrderClient;
import ru.otus.hw.dto.notification.MessageUserDto;
import ru.otus.hw.dto.order.OrderDto;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountFacade {

    private final AccountService accountService;

    private final OrderClient orderClient;

    private final NotificationClient notificationClient;

    public List<OrderDto> getAllOrderByLogin(String login) {
        val accountDto = accountService.findAllByLogin(login);
        return orderClient.getOrderByLogin(accountDto.getLogin());
    }

    public List<MessageUserDto> getAllNotificationByLogin(String login) {
        val accountDto = accountService.findAllByLogin(login);
        return notificationClient.getAllMessageUserByLogin(accountDto.getLogin());
    }
}
