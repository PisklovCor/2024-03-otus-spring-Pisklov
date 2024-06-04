package ru.otus.hw.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document
public class Comment {

    @Id
    private String id;

    private String content;

    private Book book;

    public Comment(String content, Book book) {
        this.content = content;
        this.book = book;
    }
}
