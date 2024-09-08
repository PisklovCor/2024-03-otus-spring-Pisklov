package ru.otus.hw.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;
import ru.otus.hw.dto.RoleDto;
import ru.otus.hw.dto.UserDto;
import ru.otus.hw.exceptions.NotFoundException;

@Slf4j
@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsManager {

    private final UserService userService;

    @Override
    public void createUser(UserDetails user) {

    }

    @Override
    public void updateUser(UserDetails user) {

    }

    @Override
    public void deleteUser(String username) {

    }

    @Override
    public void changePassword(String oldPassword, String newPassword) {

        ////BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(8);
        //    //String hashedAdmin = passwordEncoder.encode("admin");
        //    //String hashedPassword = passwordEncoder.encode("password");

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
}
