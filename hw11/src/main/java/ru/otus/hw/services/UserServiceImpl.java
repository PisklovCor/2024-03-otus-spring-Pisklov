package ru.otus.hw.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.hw.dto.UserDto;
import ru.otus.hw.exceptions.NotFoundException;
import ru.otus.hw.mappers.UserMapper;
import ru.otus.hw.repositories.UserRepository;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    @Override
    public UserDto findByLogin(String login) {

        var user = userRepository.findByLogin(login)
                .orElseThrow(() -> new NotFoundException("User with login %s not found".formatted(login)));

        return userMapper.toDto(user);
    }
}
