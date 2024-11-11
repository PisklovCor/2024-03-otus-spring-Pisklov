package ru.otus.hw.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthorDto {

    /**
     * Identifier author.
     */
    private long id;

    /**
     * Full name author.
     */
    private String fullName;
}
