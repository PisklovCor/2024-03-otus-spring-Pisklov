package ru.otus.hw.events;

import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;
import ru.otus.hw.models.secondary.Genre;

import java.util.UUID;

@Component
public class UuidIdentifiedGenreEventListener extends AbstractMongoEventListener<Genre> {

    @Override
    public void onBeforeConvert(BeforeConvertEvent<Genre> event) {

        super.onBeforeConvert(event);
        Genre entity = event.getSource();

        if (entity.getId() == null) {
            entity.setId(UUID.randomUUID().toString());
        }
    }
}
