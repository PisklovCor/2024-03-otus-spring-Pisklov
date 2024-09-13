package ru.otus.hw.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw.dto.RoleDto;
import ru.otus.hw.dto.UserDto;
import ru.otus.hw.exceptions.NotFoundException;
import ru.otus.hw.mappers.UserMapper;
import ru.otus.hw.models.Role;
import ru.otus.hw.repositories.RoleRepository;
import ru.otus.hw.repositories.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.util.CollectionUtils.isEmpty;

@Slf4j
@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsManager {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final UserMapper userMapper;

    @Override
    @Transactional
    public void createUser(UserDetails user) {

        userRepository.save(
                new ru.otus.hw.models.User(null,
                        user.getUsername(),
                        encryptPassword(user.getPassword()),
                        checkingAndSearchingRoles(user)));
    }

    @Override
    @Transactional
    public void updateUser(UserDetails user) {

        final String login = user.getUsername();
        var userEntity = userRepository.findByLogin(login)
                .orElseThrow(() -> new NotFoundException("User with username %s not found".formatted(login)));

        userEntity.setLogin(login);
        userEntity.setPassword(encryptPassword(user.getPassword()));
        userEntity.setRoles(checkingAndSearchingRoles(user));

        userRepository.save(userEntity);
    }

    @Override
    @Transactional
    public void deleteUser(String username) {

        var userDto = findByLoginAndConvertToUserDto(username);
        userRepository.deleteById(userDto.getId());
    }

    @Override
    @Transactional
    public void changePassword(String oldPassword, String newPassword) {

        if (oldPassword.equals(newPassword)) {
            throw new RuntimeException("Passwords must be different");
        }

        SecurityContextHolderStrategy strategy =
                SecurityContextHolder.getContextHolderStrategy();

        var userDetails = (UserDetails) strategy.getContext()
                .getAuthentication().getPrincipal();

        final String login = userDetails.getUsername();
        var userEntity = userRepository.findByLogin(login)
                .orElseThrow(() -> new NotFoundException("User with username %s not found".formatted(login)));

        userEntity.setLogin(login);
        userEntity.setPassword(encryptPassword(newPassword));
        userEntity.setRoles(checkingAndSearchingRoles(userDetails));

        userRepository.save(userEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean userExists(String username) {

        try {
            findByLoginAndConvertToUserDto(username);
            return true;
        } catch (NotFoundException e) {
            log.warn("User [{}] not found", username);
            return false;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        var userDto = findByLoginAndConvertToUserDto(username);
        return createUserDetails(userDto);
    }

    /**
     * Созадние объекта дял контекста Security
     * {@link org.springframework.security.core.userdetails.UserDetails}
     */
    private UserDetails createUserDetails(UserDto userDto) {

        var roles = userDto.getRoles().stream()
                .map(RoleDto::getName)
                .toList();

        return User.builder()
                .username(userDto.getLogin())
                .password(userDto.getPassword())
                .roles(StringUtils.join(roles, ", "))
                .build();
    }

    /**
     * Выполнение шифрования пароля
     *
     * @param password пароль с формы
     * @return зашифрованный пароль
     */
    private String encryptPassword(String password) {

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(12);
        return passwordEncoder.encode(password);
    }

    /**
     * Выполнение поиска через репозиторий и конвертация в DTO
     * Необходимо что бы в контексте не оставалось Entity.User
     *
     * @param login логин пользоваетля
     * @return ДТО объекта пользователя
     */
    private UserDto findByLoginAndConvertToUserDto(String login) {
        return userMapper.toDto(userRepository.findByLogin(login)
                .orElseThrow(() -> new NotFoundException("User with username %s not found".formatted(login))));
    }

    /**
     * Получение ролей Spring, преоброзование в объект {@link ru.otus.hw.models.Role} и проверка по базе данных
     *
     * @param userDetails Provides core user information.
     * @return коллекция ролей пользователя
     */
    private List<Role> checkingAndSearchingRoles(UserDetails userDetails) {

        var roleNameList = userDetails.getAuthorities().stream()
                .map(r -> r.toString().replace("ROLE_", ""))
                .collect(Collectors.toSet());

        if (isEmpty(roleNameList)) {
            throw new IllegalArgumentException("Roles must not be null");
        }

        var roles = roleRepository.findByNameIn(roleNameList);
        if (isEmpty(roles) || roles.size() != roleNameList.size()) {
            throw new NotFoundException("One or all roles with names %s not found".formatted(roleNameList));
        }

        return roles;
    }
}
