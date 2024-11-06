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

    @NotNull
    private Long id;

    @NotBlank
    private String title;

    private AuthorDto author;

    private List<GenreDto> genres;
}
