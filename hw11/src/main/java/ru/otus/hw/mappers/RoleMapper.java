package ru.otus.hw.mappers;

import org.springframework.stereotype.Component;
import ru.otus.hw.dto.RoleDto;
import ru.otus.hw.models.Role;

@Component
public class RoleMapper {

    public RoleDto toDto(Role entity) {
        RoleDto dto = new RoleDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());
        return  dto;
    }
}
