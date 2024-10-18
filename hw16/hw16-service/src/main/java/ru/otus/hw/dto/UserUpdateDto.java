package ru.otus.hw.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdateDto {

    @NotNull
    private long id;

    @NotBlank
    @Size(max = 20, message = "Не больше 20 знаков")
    private String login;

    @NotBlank
    private String password;

    @NotNull
    private List<RoleDto> roles;
}
