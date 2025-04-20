package com.ecommerce.authservice.security;

import com.ecommerce.authservice.constant.Constants;
import com.ecommerce.authservice.entity.RoleEntity;
import com.ecommerce.authservice.entity.UserEntity;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Getter
@RequiredArgsConstructor
public class UserDetailsImpl implements UserDetails {

    private final UserEntity user;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        RoleEntity role = user.getRole();
        GrantedAuthority authority = new SimpleGrantedAuthority(Constants.ROLE_PREFIX + role.getRole().name());
        return List.of(authority);
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return user.getAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return user.getAccountNonLocked();
    }

    @Override
    public boolean isEnabled() {
        return user.getEnabled();
    }
}
