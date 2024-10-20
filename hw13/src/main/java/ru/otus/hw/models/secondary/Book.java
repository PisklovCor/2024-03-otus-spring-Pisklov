package ru.otus.hw.models.secondary;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document
public class Book {

    @Id
    private String id;

    private String title;

    private Author author;

    private List<Genre> genres;

    public Book(String title, Author author, Genre... genres) {
        this.title = title;
        this.author = author;
        this.genres = Arrays.asList(genres);
    }
}
