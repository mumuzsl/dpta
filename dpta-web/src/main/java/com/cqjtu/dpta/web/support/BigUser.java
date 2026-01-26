package com.cqjtu.dpta.web.support;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * author: mumu
 * date: 2021/4/26
 */
public class BigUser extends User implements OAuth2AuthenticatedPrincipal {
    private final Map<String, Object> attributes = new HashMap<>();

    private Long id;

    public BigUser(Long id, String username, String password,
                   Collection<? extends GrantedAuthority> authorities) {
        super(username, password, true, true, true, true, authorities);
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public BigUser getBigUser() {
        return this;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public String getName() {
        return super.getUsername();
    }
}
