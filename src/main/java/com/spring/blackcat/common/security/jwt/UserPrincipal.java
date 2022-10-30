package com.spring.blackcat.common.security.jwt;

import com.spring.blackcat.user.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class UserPrincipal implements UserDetails {

    private final Long userId;
//    private final Collection<? extends GrantedAuthority> authorities;

    public UserPrincipal(Long userId) {
        this.userId = userId;
//        this.authorities = authorities;
    }

    public static UserPrincipal create(User user) {
        //@TODO: 권한 추가
//        var authorities = Collections.singletonList(new SimpleGrantedAuthority(USER.name()));
        return new UserPrincipal(user.getId());
    }

    public Long getUserId() {
        return userId;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
//        return authorities;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }

}
