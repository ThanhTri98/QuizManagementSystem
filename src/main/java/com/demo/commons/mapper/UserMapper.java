package com.demo.commons.mapper;

import com.demo.models.dtos.UserDTO;
import com.demo.models.entities.UserEntity;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @author 165139
 */
public class UserMapper {
    private UserMapper() {
    }

    public static UserDTO toDTO(UserEntity entity) {
        if (entity == null) {
            return null;
        }

        var dto = new UserDTO();
        dto.setUserId(entity.getUserId());
        dto.setEmail(entity.getEmail());
        dto.setUserName(entity.getUserName());
        dto.setRoleId(entity.getRole().getRoleId());
        return dto;
    }

    public static List<UserDTO> toDTO(List<UserEntity> entities) {
        if (CollectionUtils.isEmpty(entities)) {
            return List.of();
        }

        return entities.stream().map(UserMapper::toDTO).toList();
    }

    public static UserEntity toEntity(UserDTO dto) {
        if (dto == null) {
            return null;
        }

        var entity = new UserEntity();
        entity.setUserName(dto.getUserName());
        entity.setEmail(dto.getEmail());
        entity.setPassword(dto.getPassword());
        return entity;
    }

}
