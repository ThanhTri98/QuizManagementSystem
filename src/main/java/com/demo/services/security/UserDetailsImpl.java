package com.demo.services.security;

import com.demo.models.entities.RoleEntity;
import com.demo.models.entities.UserEntity;
import lombok.Getter;
import lombok.NonNull;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;

/**
 * @author 165139
 */
public class UserDetailsImpl implements UserDetails {
    @Getter
    private final String email;
    private final String password;
    private final String userName;
    private final RoleEntity role;

    public UserDetailsImpl(@NonNull UserEntity user) {
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.userName = user.getUserName();
        this.role = user.getRole();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Set.of(new SimpleGrantedAuthority(String.format("ROLE_%s", this.role.getRoleName())));
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
