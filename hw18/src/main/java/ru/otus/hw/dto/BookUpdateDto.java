package ru.otus.hw.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookUpdateDto {

    @NotBlank
    private String id;

    @NotBlank
    private String title;

    @NotBlank
    private String authorId;

    @NotNull
    private List<String> genresId;
}
