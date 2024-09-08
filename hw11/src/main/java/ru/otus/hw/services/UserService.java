package ru.otus.hw.services;

import ru.otus.hw.dto.UserDto;
import ru.otus.hw.dto.UserCreateDto;
import ru.otus.hw.dto.UserUpdateDto;

public interface UserService {

    UserDto findByLogin(String login);

    UserDto create(UserCreateDto dto);

    UserDto update(UserUpdateDto dto);

    void deleteById(long id);
}
