package com.demo.repositories;

import com.demo.models.entities.UserJwtEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author 165139
 */
@Repository
public interface UserJwtRepository extends JpaRepository<UserJwtEntity, Integer> {
    Optional<UserJwtEntity> findUserJwtEntityByUserUserIdAndJwt(int userId, String jwt);

    Optional<UserJwtEntity> findUserJwtEntityByUserUserId(int userId);
}
