package com.demo.services;

import com.demo.commons.mapper.RoleMapper;
import com.demo.models.dtos.RoleDTO;
import com.demo.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author 165139
 */
@Service
public class RoleService {
    private final RoleRepository roleRepository;

    @Autowired
    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Optional<RoleDTO> findById(int roleId) {
        return this.roleRepository.findById(roleId).map(RoleMapper::toDTO);
    }

}
