package ru.otus.hw.mongock.changelog;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.mongodb.client.MongoDatabase;
import ru.otus.hw.models.secondary.Author;
import ru.otus.hw.models.secondary.Book;
import ru.otus.hw.models.secondary.Genre;
import ru.otus.hw.repositories.secondary.AuthorMongoRepository;
import ru.otus.hw.repositories.secondary.BookMongoRepository;
import ru.otus.hw.repositories.secondary.GenreMongoRepository;


@ChangeLog(order = "001")
public class DatabaseChangelog {

    private Author erichMariaRemarque;

    private Author fyodorDostoyevsky;

    private Author ernestMillerHemingway;

    private Genre fiction;

    private Genre mystery;

    private Genre thriller;

    private Genre scienceFiction;

    private Genre fantasy;

    private Genre romance;

    @ChangeSet(order = "000", id = "dropDb", author = "pisklov", runAlways = true)
    public void dropDb(MongoDatabase db) {
        db.drop();
    }

    @ChangeSet(order = "001", id = "initAuthor", author = "pisklov")
    public void initAuthor(AuthorMongoRepository repository) {
        erichMariaRemarque = repository.save(new Author("Erich Maria Remarque"));
        fyodorDostoyevsky = repository.save(new Author("Fyodor Dostoyevsky"));
        ernestMillerHemingway = repository.save(new Author("Ernest Miller Hemingway"));
    }

    @ChangeSet(order = "002", id = "initGenres", author = "pisklov")
    public void initGenres(GenreMongoRepository repository) {

        fiction = repository.save(new Genre("Fiction"));
        mystery = repository.save(new Genre("Mystery"));
        thriller = repository.save(new Genre("Thriller"));
        scienceFiction = repository.save(new Genre("Science fiction"));
        fantasy = repository.save(new Genre("Fantasy"));
        romance = repository.save(new Genre("Romance"));
    }

    @ChangeSet(order = "003", id = "initBook", author = "pisklov")
    public void initBook(BookMongoRepository repository) {
        repository.save(new Book("Im Westen nichts Neues", erichMariaRemarque, fiction, mystery));
        repository.save(new Book("The idiot", fyodorDostoyevsky, thriller, scienceFiction));
        repository.save(new Book("For Whom the Bell Tolls", ernestMillerHemingway, fantasy, romance));
    }
}
