package ru.otus.hw.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookUpdateDto {

    /**
     * Identifier book.
     */
    @NotNull
    private long id;

    /**
     * Title book.
     */
    @NotBlank
    private String title;

    /**
     * Identifier author of the book.
     */
    @NotNull
    private long authorId;

    /**
     * Identifier book genres.
     */
    @NotNull
    private List<Long> genresId;
}
