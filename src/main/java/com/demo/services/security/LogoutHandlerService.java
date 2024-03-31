package com.demo.services.security;

import com.demo.commons.util.JwtUtil;
import com.demo.services.UserJwtService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

/**
 * @author 165139
 */
@Service
@Slf4j
public class LogoutHandlerService implements LogoutHandler {
    private final UserJwtService userJwtService;

    public LogoutHandlerService(UserJwtService userJwtService) {
        this.userJwtService = userJwtService;
    }

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        try {
            String jwt = JwtUtil.getJwtFromRequestHeader(request);
            if (jwt != null && this.userJwtService.isValidJwt(jwt)) {
                String userId = this.userJwtService.getJwtTokenProvider().getUserIdFromJWT(jwt);
                this.userJwtService.upsert(Integer.parseInt(userId), null);
            } else {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "UnAuthorized");
            }
        } catch (Exception ex) {
            log.error("Logout ex {0}", ex.fillInStackTrace());
        } finally {
            SecurityContextHolder.clearContext();
        }

    }
}