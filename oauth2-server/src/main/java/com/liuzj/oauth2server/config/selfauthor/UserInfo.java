package com.liuzj.oauth2server.config.selfauthor;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * 注意：此类必须可序列化，因为要进行IO传输
 *
 * @author liuzj
 * @date  2019-01-15
 */
public class UserInfo implements UserDetails, Serializable {

    private String userName;

    private String password;

    private String role;

    public UserInfo(String userName, String password, String role) {
        this.password = password;
        this.userName = userName;
        this.role = role;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> auths = new java.util.ArrayList<>();
        auths.add(new SimpleGrantedAuthority(role));
        return auths;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return userName;
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
