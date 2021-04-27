package com.cqjtu.dpta.web.service;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

/**
 * author: mumu
 * date: 2021/4/26
 */
public abstract class AbstractUserDetailsService implements UserDetailsService {

    private List<GrantedAuthority> authorities;

    public AbstractUserDetailsService() {
        authorities = AuthorityUtils.createAuthorityList(getRole());
    }

    protected abstract String[] getRole();

    protected void addRole(String role) {
        authorities.add(new SimpleGrantedAuthority(role));
    }

    protected List<GrantedAuthority> getAuthorities() {
        return authorities;
    }

    protected void user(){

    }
}
