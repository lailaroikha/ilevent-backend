package com.ilevent.ilevent_backend.auth.entity;

import com.ilevent.ilevent_backend.users.entity.Users;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;


@Getter
public class UserAuth extends Users implements UserDetails {
    private final Users users;

    public UserAuth(Users users){
        this.users =users;
    }

    @Override
    public String getPassword() {
        return users.getPassword();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        ArrayList<GrantedAuthority> authorities = new ArrayList<>();
        if (Boolean.TRUE.equals(users.getOrganizer())) {
        // Add role based on is_organizer field
//        if (users.isOrganizer()) {
            authorities.add(new SimpleGrantedAuthority("ROLE_ORGANIZER"));
        } else {
            authorities.add(new SimpleGrantedAuthority("ROLE_PERSONAL"));
        }
        return authorities;
    }

    @Override
    public String getUsername() {
        return users.getEmail();
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
