package com.demo.commons.mapper;

import com.demo.models.dtos.RoleDTO;
import com.demo.models.entities.RoleEntity;

/**
 * @author 165139
 */
public class RoleMapper {
    private RoleMapper() {
    }

    public static RoleDTO toDTO(RoleEntity entity) {
        if (entity == null) {
            return null;
        }

        var dto = new RoleDTO();
        dto.setRoleId(entity.getRoleId());
        dto.setRoleName(entity.getRoleName());
        return dto;
    }

    public static RoleEntity toEntity(RoleDTO dto) {
        if (dto == null) {
            return null;
        }

        var entity = new RoleEntity();
        entity.setRoleId(dto.getRoleId());
        entity.setRoleName(dto.getRoleName());
        return entity;
    }

}
