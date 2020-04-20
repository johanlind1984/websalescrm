package com.johanlind.websalescrm.Services;

import com.johanlind.websalescrm.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class PrincipalUser implements UserDetails {

    private String userName;
    private String passWord;
    private List<GrantedAuthority> grantedAuthority;
    private boolean isEnabled;

    public PrincipalUser(User user) {
        this.userName = user.getUserName();
        this.passWord = user.getPassword();
        this.isEnabled = user.isEnabled();
        grantedAuthority = new ArrayList<>();
        grantedAuthority.add(new SimpleGrantedAuthority(user.getAuthority().getAuthorityName()));
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return grantedAuthority;
    }

    @Override
    public String getPassword() {
        return passWord;
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
        return isEnabled;
    }
}
