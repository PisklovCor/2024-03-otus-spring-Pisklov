package ru.otus.hw.events;

import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;
import ru.otus.hw.models.secondary.Book;

import java.util.UUID;

@Component
public class UuidIdentifiedBookEventListener extends AbstractMongoEventListener<Book> {

    @Override
    public void onBeforeConvert(BeforeConvertEvent<Book> event) {

        super.onBeforeConvert(event);
        Book entity = event.getSource();

        if (entity.getId() == null) {
            entity.setId(UUID.randomUUID().toString());
        }
    }
}
