package ru.otus.hw.events;

import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;
import ru.otus.hw.models.secondary.Author;

import java.util.UUID;

@Component
public class UuidIdentifiedAuthorEventListener extends AbstractMongoEventListener<Author> {

    @Override
    public void onBeforeConvert(BeforeConvertEvent<Author> event) {

        super.onBeforeConvert(event);
        Author entity = event.getSource();

        if (entity.getId() == null) {
            entity.setId(UUID.randomUUID().toString());
        }
    }
}
