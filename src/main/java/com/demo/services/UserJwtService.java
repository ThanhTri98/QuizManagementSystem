package com.demo.services;

import com.demo.models.entities.UserJwtEntity;
import com.demo.repositories.UserJwtRepository;
import com.demo.repositories.UserRepository;
import com.demo.services.security.JwtTokenProvider;
import lombok.Getter;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author 165139
 */
@Service
public class UserJwtService {
    private final UserJwtRepository userJwtRepository;
    private final UserRepository userRepository;
    @Getter
    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public UserJwtService(UserJwtRepository userJwtRepository, UserRepository userRepository, JwtTokenProvider jwtTokenProvider) {
        this.userJwtRepository = userJwtRepository;
        this.userRepository = userRepository;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public boolean isValidJwt(@NonNull String jwt) {
        String uId = this.jwtTokenProvider.getUserIdFromJWT(jwt);
        if (!StringUtils.hasText(uId)) {
            throw new IllegalArgumentException("UserId not found in JWT!");
        }
        var entity = this.userJwtRepository.findUserJwtEntityByUserUserId(Integer.parseInt(uId));
        return entity.filter(userJwtEntity -> jwt.equals(userJwtEntity.getJwt()) && this.jwtTokenProvider.validateToken(jwt)).isPresent();
    }

    public void upsert(int userId, String jwt) {
        AtomicInteger userIdAtomic = new AtomicInteger(userId);
        if (userId == 0 && jwt != null) {
            userId = Integer.parseInt(this.jwtTokenProvider.getUserIdFromJWT(jwt));
        }
        userIdAtomic.set(userId);

        this.userJwtRepository.findUserJwtEntityByUserUserId(userId)
                .ifPresentOrElse(entity -> {
                    entity.setJwt(jwt);
                    entity.setModifiedDate(new Date());
                    this.userJwtRepository.save(entity);
                }, () -> this.userRepository.findById(userIdAtomic.get())
                        .ifPresentOrElse(userE -> {
                            UserJwtEntity entity = new UserJwtEntity();
                            entity.setUser(userE);
                            entity.setJwt(jwt);
                            entity.setModifiedDate(new Date());
                            this.userJwtRepository.save(entity);
                        }, () -> {
                            throw new RuntimeException("User not found!");
                        }));
    }

}
