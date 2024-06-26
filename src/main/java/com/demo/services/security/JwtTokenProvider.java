package com.demo.services.security;

import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author 165139
 */
@Component
@Slf4j
public class JwtTokenProvider {
    @Value("${config.jwt.secret}")
    private String jwtSecret;
    @Value("${config.jwt.expiration}")
    private long jwtExpiration;

    // Tạo ra jwt từ thông tin user
    public String generateToken(int userId, String email) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpiration);
        // Tạo chuỗi json web token từ id của user.
        return Jwts.builder()
                .setSubject(email)
                .setId(String.valueOf(userId))
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    // Lấy thông tin user email từ jwt
    public String getEmailFromJWT(String jwt) {
        return Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(jwt)
                .getBody()
                .getSubject();
    }

    public String getUserIdFromJWT(String jwt) {
        return Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(jwt)
                .getBody()
                .getId();
    }

    public boolean validateToken(String jwt) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(jwt);
            log.debug("validate success {}", jwt);
            return true;
        } catch (MalformedJwtException ex) {
            log.error("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            log.error("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            log.error("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            log.error("JWT claims string is empty.");
        } catch (SignatureException ex) {
            log.error("JWT SignatureException.");
        }
        return false;
    }
}
