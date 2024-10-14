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
public class BookCreateDto {

    @NotBlank
    private String title;

    @NotNull
    private long authorId;

    @NotNull
    private List<Long> genresId;

}
