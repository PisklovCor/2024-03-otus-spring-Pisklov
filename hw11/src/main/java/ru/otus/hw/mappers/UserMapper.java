package ru.otus.hw.mappers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.hw.dto.UserDto;
import ru.otus.hw.dto.RoleDto;
import ru.otus.hw.models.User;

import java.util.List;

@RequiredArgsConstructor
@Component
public class UserMapper {

    private final RoleMapper roleMapper;

    public UserDto toDto(User entity) {
        List<RoleDto> roleDtoList = entity.getRoles().stream().map(roleMapper::toDto).toList();
        UserDto dto = new UserDto();
        dto.setId(entity.getId());
        dto.setLogin(entity.getLogin());
        dto.setPassword(entity.getPassword());
        dto.setRoles(roleDtoList);
        return  dto;
    }
}
