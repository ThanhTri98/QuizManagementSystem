package com.demo.services;

import com.demo.commons.exception.UserAlreadyExistsException;
import com.demo.commons.mapper.RoleMapper;
import com.demo.commons.mapper.UserMapper;
import com.demo.models.dtos.UserDTO;
import com.demo.models.entities.UserEntity;
import com.demo.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Optional;

/**
 * @author 165139
 */
@Service
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, RoleService roleService, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
    }

    public Optional<UserDTO> findUserByEmail(String email) {
        return this.userRepository.findUserByEmail(email).map(UserMapper::toDTO);
    }

    public List<UserDTO> findAll() {
        var lst = this.userRepository.findAll();
        return CollectionUtils.isEmpty(lst) ? List.of() : UserMapper.toDTO(lst);
    }

    public UserDTO findById(int userId) {
        return this.userRepository.findById(userId).map(UserMapper::toDTO).orElse(null);
    }

    public void add(UserDTO user) throws UserAlreadyExistsException {
        // Check email not exists
        if (findUserByEmail(user.getEmail()).orElse(null) == null) {
            if (user.getRoleId() == 0) {
                throw new IllegalArgumentException("RoleId not found!");
            }

            this.roleService.findById(user.getRoleId())
                    .ifPresentOrElse(roleDTO -> {
                        // Encode password
                        UserEntity userE = UserMapper.toEntity(user);
                        userE.setPassword(this.passwordEncoder.encode(user.getPrivatePassword()));
                        userE.setRole(RoleMapper.toEntity(roleDTO));
                        this.userRepository.save(userE);
                    }, () -> {
                        throw new IllegalArgumentException("RoleId not exists!");
                    });
        } else {
            throw new UserAlreadyExistsException(user.getEmail());
        }
    }

    public void updateRole(int userId, int roleId) {
        if (userId == 0) {
            throw new IllegalArgumentException("UserId invalid!");
        }
        if (roleId == 0) {
            throw new IllegalArgumentException("RoleId invalid!");
        }

        this.userRepository.findById(userId)
                .ifPresentOrElse(userE -> {
                    if (userE.getRole().getRoleId() != roleId) {
                        this.roleService.findById(roleId)
                                .ifPresentOrElse(roleDTO -> {
                                    var roleE = RoleMapper.toEntity(roleDTO);
                                    userE.setRole(roleE);
                                    this.userRepository.save(userE);
                                }, () -> {
                                    throw new IllegalArgumentException("RoleId not found!");
                                });
                    } else {
                        log.debug("RoleId {} is not change", roleId);
                    }
                }, () -> {
                    throw new IllegalArgumentException("UserId not found!");
                });
    }

    public void deleteById(Integer id) {
        this.userRepository.deleteById(id);
    }

}
