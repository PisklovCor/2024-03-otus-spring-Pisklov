package ru.otus.hw.events;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.AfterDeleteEvent;
import org.springframework.stereotype.Component;
import reactor.core.scheduler.Scheduler;
import ru.otus.hw.models.Book;
import ru.otus.hw.repositories.CommentRepository;

@Slf4j
@Component
@RequiredArgsConstructor
public class MongoBookCascadeDeleteEventsListener extends AbstractMongoEventListener<Book> {

    private final CommentRepository repository;

    private final Scheduler workerPool;

    @Override
    public void onAfterDelete(@NotNull AfterDeleteEvent<Book> event) {
        super.onAfterDelete(event);
        val source = event.getSource();
        val id = source.get("_id").toString();
        repository.deleteAllByBookId(id).publishOn(workerPool)
                .subscribe();
    }
}
