package ru.otus.hw.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ru.otus.hw.dto.UserDto;
import ru.otus.hw.dto.UserCreateDto;
import ru.otus.hw.dto.UserUpdateDto;
import ru.otus.hw.dto.RoleDto;
import ru.otus.hw.exceptions.NotFoundException;
import ru.otus.hw.mappers.UserMapper;
import ru.otus.hw.models.Role;
import ru.otus.hw.models.User;
import ru.otus.hw.repositories.RoleRepository;
import ru.otus.hw.repositories.UserRepository;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.springframework.util.CollectionUtils.isEmpty;

@Deprecated
@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final UserMapper userMapper;

    @Override
    @Transactional(readOnly = true)
    public UserDto findByLogin(String login) {

        var user = userRepository.findByLogin(login)
                .orElseThrow(() -> new NotFoundException("User with login %s not found".formatted(login)));

        return userMapper.toDto(user);
    }

    @Override
    @Transactional
    public UserDto create(UserCreateDto dto) {
        return userMapper.toDto(userRepository.save(
                new User(null,
                        dto.getLogin(),
                        dto.getPassword(),
                        checkingAndSearchingRoles(dto.getRoles().stream()
                                .map(RoleDto::getName)
                                .collect(Collectors.toSet())))));
    }

    @Override
    @Transactional
    public UserDto update(UserUpdateDto dto) {

        final long userId = dto.getId();

        var userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User with id %d not found".formatted(userId)));

        userEntity.setLogin(dto.getLogin());
        userEntity.setPassword(dto.getPassword());
        userEntity.setRoles(checkingAndSearchingRoles(dto.getRoles().stream()
                .map(RoleDto::getName)
                .collect(Collectors.toSet())));

        return userMapper.toDto(userRepository.save(userEntity));
    }

    @Override
    @Transactional
    public void deleteById(long id) {
        userRepository.deleteById(id);

    }

    private List<Role> checkingAndSearchingRoles(Set<String> rolesName) {
        if (isEmpty(rolesName)) {
            throw new IllegalArgumentException("Roles names must not be null");
        }

        var roles = roleRepository.findByNameIn(rolesName);
        if (isEmpty(roles) || roles.size() != rolesName.size()) {
            throw new NotFoundException("One or all roles with names %s not found".formatted(rolesName));
        }

        return roles;
    }
}
