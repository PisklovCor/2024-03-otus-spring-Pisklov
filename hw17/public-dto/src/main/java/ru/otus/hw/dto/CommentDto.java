package ru.otus.hw.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto {

    /**
     * Identifier comment.
     */
    private long id;

    /**
     * Content comment.
     */
    private String content;
}
