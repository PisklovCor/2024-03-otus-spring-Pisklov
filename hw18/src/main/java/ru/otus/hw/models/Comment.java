package ru.otus.hw.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document
public class Comment {


    @Id
    private String id;

    private String content;

    @DBRef(lazy = true)
    private Book book;

    public Comment(String content, Book book) {
        this.content = content;
        this.book = book;
    }
}
