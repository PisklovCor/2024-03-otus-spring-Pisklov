package ru.otus.hw.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GenreDto {

    /**
     * Identifier genre.
     */
    private long id;


    /**
     * Name genre.
     */
    private String name;
}
