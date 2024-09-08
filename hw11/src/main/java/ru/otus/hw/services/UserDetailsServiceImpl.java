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
import ru.otus.hw.dto.RoleDto;
import ru.otus.hw.dto.UserCreateDto;
import ru.otus.hw.dto.UserDto;
import ru.otus.hw.dto.UserUpdateDto;
import ru.otus.hw.exceptions.NotFoundException;

@Slf4j
@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsManager {

    private final UserService userService;

    @Override
    public void createUser(UserDetails user) {

        var roleDtoList = user.getAuthorities().stream()
                .map(r -> new RoleDto(null, r.toString().replace("ROLE_", ""), null))
                .toList();

        var userCreateDto = new UserCreateDto(
                user.getUsername(),
                encryptPassword(user.getPassword()),
                roleDtoList
        );

        userService.create(userCreateDto);
    }

    @Override
    public void updateUser(UserDetails user) {

        var userDto = userService.findByLogin(user.getUsername());

        var roleDtoList = user.getAuthorities().stream()
                .map(r -> new RoleDto(null, r.toString().replace("ROLE_", ""), null))
                .toList();

        var userUpdateDto = new UserUpdateDto(
                userDto.getId(),
                user.getUsername(),
                encryptPassword(user.getPassword()),
                roleDtoList
        );

        userService.update(userUpdateDto);
    }

    @Override
    public void deleteUser(String username) {

        var userDto = userService.findByLogin(username);
        userService.deleteById(userDto.getId());
    }

    @Override
    public void changePassword(String oldPassword, String newPassword) {

        if (oldPassword.equals(newPassword)) {
            throw new RuntimeException("Passwords must be different");
        }

        SecurityContextHolderStrategy strategy =
                SecurityContextHolder.getContextHolderStrategy();

        var userDetails = (UserDetails) strategy.getContext()
                .getAuthentication().getPrincipal();

        var userDto = userService.findByLogin(userDetails.getUsername());

        var userUpdateDto = new UserUpdateDto(
                userDto.getId(),
                userDto.getLogin(),
                encryptPassword(newPassword),
                userDto.getRoles()
        );

        userService.update(userUpdateDto);

    }

    @Override
    public boolean userExists(String username) {

        try {
            userService.findByLogin(username);
            return true;
        } catch (NotFoundException e) {
            log.warn("User [{}] not found", username);
            return false;
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        var userDto = userService.findByLogin(username);
        return createUserDetails(userDto);
    }

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
}
