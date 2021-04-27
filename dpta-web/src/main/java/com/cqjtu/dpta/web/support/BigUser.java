package com.cqjtu.dpta.web.support;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

/**
 * author: mumu
 * date: 2021/4/26
 */
public class BigUser extends User {
    private Long id;


    public BigUser(Long id, String username, String password,
                   Collection<? extends GrantedAuthority> authorities) {
        super(username, password, true, true, true, true, authorities);
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
