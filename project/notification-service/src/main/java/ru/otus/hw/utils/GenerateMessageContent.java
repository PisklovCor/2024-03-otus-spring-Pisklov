package ru.otus.hw.utils;

import lombok.experimental.UtilityClass;
import ru.otus.hw.dto.account.AccountDto;
import ru.otus.hw.models.RawMessage;

import static ru.otus.hw.dictionaries.MessageType.CREATION;

@UtilityClass
public class GenerateMessageContent {

    public String generateMessageContentUser(RawMessage rawMessage, AccountDto accountDto) {

        return "Здравствуйте " + accountDto.getName() + " " + accountDto.getSurname() + ". "
                + definitionOfTheSystem(rawMessage);
    }

    private String definitionOfTheSystem(RawMessage rawMessage) {

        return switch (rawMessage.getExternalSystemName()) {
            case ORDER_SERVICE -> messageOrderService(rawMessage);
            case LIBRARY_SERVICE -> "Вы оставили комментарий: " + rawMessage.getContent();
            case ACCOUNT_SERVICE -> "Вы получили это сообщение" +
                    " потому, что стали обладателм книги:" + rawMessage.getContent();
            default -> "";
        };
    }

    private String messageOrderService(RawMessage rawMessage) {
        var messageSource = "";
        messageSource = "Вы оставили заказ на книгу: " + rawMessage.getContent() + ". ";
        if (rawMessage.getMessageType().equals(CREATION)) {
            messageSource = messageSource + "Ваш заказ успешно создан. Ожидайте!";
        } else {
            messageSource = messageSource + "Книга уже доступна в нашей библиотке!";
        }
        return messageSource;
    }
}
