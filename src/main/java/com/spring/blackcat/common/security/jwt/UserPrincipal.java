package com.spring.blackcat.common.security.jwt;

import com.spring.blackcat.user.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

public class UserPrincipal implements UserDetails {

    //    private final Long userId;
//    private final Collection<? extends GrantedAuthority> authorities;
    private final User user;

    public UserPrincipal(User user) {
        this.user = user;
//        this.authorities = authorities;
    }

    public static UserPrincipal create(User user) {
        // TODO: 권한 추가
//        var authorities = Collections.singletonList(new SimpleGrantedAuthority(USER.name()));
        return new UserPrincipal(user);
    }

    public Long getUserId() {
        return user.getId();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + user.getRole()));
        return authorities;
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
