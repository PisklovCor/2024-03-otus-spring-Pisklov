package ru.otus.hw.mongock.changelog;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.mongodb.client.MongoDatabase;
import ru.otus.hw.exceptions.NotFoundException;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Comment;
import ru.otus.hw.models.Genre;
import ru.otus.hw.repositories.AuthorRepository;
import ru.otus.hw.repositories.BookRepository;
import ru.otus.hw.repositories.CommentRepository;
import ru.otus.hw.repositories.GenreRepository;

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

    private Book imWestenNichtsNeues;

    private Book theIdiot;

    private Book forWhomTheBellTolls;

    @ChangeSet(order = "000", id = "dropDb", author = "pisklov", runAlways = true)
    public void dropDb(MongoDatabase db) {
        db.drop();
    }

    @ChangeSet(order = "001", id = "initAuthor", author = "pisklov")
    public void initAuthor(AuthorRepository repository) {
        erichMariaRemarque = repository.save(new Author("Erich Maria Remarque"))
                .blockOptional().orElseThrow(() -> new NotFoundException("Result is empty"));
        fyodorDostoyevsky = repository.save(new Author("Fyodor Dostoyevsky"))
                .blockOptional().orElseThrow(() -> new NotFoundException("Result is empty"));
        ernestMillerHemingway = repository.save(new Author("Ernest Miller Hemingway"))
                .blockOptional().orElseThrow(() -> new NotFoundException("Result is empty"));
    }

    @ChangeSet(order = "002", id = "initGenres", author = "pisklov")
    public void initGenres(GenreRepository repository) {

        fiction = repository.save(new Genre("Fiction"))
                .blockOptional().orElseThrow(() -> new NotFoundException("Result is empty"));
        mystery = repository.save(new Genre("Mystery"))
                .blockOptional().orElseThrow(() -> new NotFoundException("Result is empty"));
        thriller = repository.save(new Genre("Thriller"))
                .blockOptional().orElseThrow(() -> new NotFoundException("Result is empty"));
        scienceFiction = repository.save(new Genre("Science fiction"))
                .blockOptional().orElseThrow(() -> new NotFoundException("Result is empty"));
        fantasy = repository.save(new Genre("Fantasy"))
                .blockOptional().orElseThrow(() -> new NotFoundException("Result is empty"));
        romance = repository.save(new Genre("Romance"))
                .blockOptional().orElseThrow(() -> new NotFoundException("Result is empty"));
    }

    @ChangeSet(order = "003", id = "initBook", author = "pisklov")
    public void initBook(BookRepository repository) {
        imWestenNichtsNeues = repository.save(
                new Book("Im Westen nichts Neues", erichMariaRemarque, fiction, mystery))
                .blockOptional().orElseThrow(() -> new NotFoundException("Result is empty"));

        theIdiot = repository.save(new Book("The idiot", fyodorDostoyevsky, thriller, scienceFiction))
                .blockOptional().orElseThrow(() -> new NotFoundException("Result is empty"));
        forWhomTheBellTolls = repository.save(
                new Book("For Whom the Bell Tolls", ernestMillerHemingway, fantasy, romance))
                .blockOptional().orElseThrow(() -> new NotFoundException("Result is empty"));
    }

    @ChangeSet(order = "004", id = "initComment", author = "pisklov")
    public void initComment(CommentRepository repository) {
        repository.save(new Comment("There is something about yourself that you don't know. - Jason Statham"
                , imWestenNichtsNeues)).block();
        repository.save(new Comment("People take chances every now and then," +
                " and you don't want to disappoint them. - Jason Statham", imWestenNichtsNeues)).block();
        repository.save(new Comment("I love people who have a good sense of humor, tell a good story," +
                " tell a good joke. - Jason Statham", theIdiot)).block();
        repository.save(new Comment("When I'm getting ready for a movie," +
                " let's just say my diet is 'The Antisocial Diet.- Jason Statham", theIdiot)).block();
        repository.save(new Comment("I've come from nowhere," +
                " and I'm not shy to go back. - Jason Statham", forWhomTheBellTolls)).block();
        repository.save(new Comment("You can''t be pretentious about what we do," +
                " because at the end of the day, movies are about entertainment," +
                "        ' and if people get 10 dollars'' worth," +
                " then that''s okay. - Jason Statham", forWhomTheBellTolls)).block();
    }
}
