package com.demo.configuration;

import com.demo.commons.util.JwtUtil;
import com.demo.services.UserJwtService;
import com.demo.services.security.UserDetailsServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Map;

/**
 * @author 165139
 */
@Component
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final UserDetailsServiceImpl userDetailsService;
    private final UserJwtService userJwtService;

    @Autowired
    public JwtAuthenticationFilter(UserDetailsServiceImpl userDetailsService,
                                   UserJwtService userJwtService) {
        this.userDetailsService = userDetailsService;
        this.userJwtService = userJwtService;
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws IOException {
        try {
            if (isPassFilter(request)) {
                filterChain.doFilter(request, response);
                return;
            }
            // Lấy jwt từ request
            String jwt = JwtUtil.getJwtFromRequestHeader(request);
            if (StringUtils.hasText(jwt) && this.userJwtService.isValidJwt(jwt)) {
                // Lấy id user từ chuỗi jwt
                String email = this.userJwtService.getJwtTokenProvider().getEmailFromJWT(jwt);
                // Lấy thông tin người dùng từ id
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(email);
                if (userDetails != null) {
                    // Nếu người dùng hợp lệ, set thông tin cho Security Context
                    UsernamePasswordAuthenticationToken
                            authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            } else {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "UnAuthorized");
                return;
            }

            filterChain.doFilter(request, response);
        } catch (Exception ex) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "UnAuthorized");
            log.error("Failed on set user authentication", ex);
        }

    }

    private final Map<String, HttpMethod> passFilterMap = Map.of(
            "/api/v1/auth/login", HttpMethod.POST,
            "/", HttpMethod.GET
    );

    private boolean isPassFilter(HttpServletRequest request) {
        final String path = request.getServletPath();
        final HttpMethod method = HttpMethod.valueOf(request.getMethod());
        return this.passFilterMap.entrySet().stream()
                .filter(k -> k.getValue() == method)
                .filter(v -> v.getKey().equals(path))
                .findFirst()
                .orElse(null) != null;
    }

}
