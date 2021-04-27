package com.cqjtu.dpta.web.service;

import com.cqjtu.dpta.api.UserService;
import com.cqjtu.dpta.dao.entity.User;
import com.cqjtu.dpta.web.support.BigUser;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * author: mumu
 * date: 2021/4/26
 */
@Service
public class AdminUserDetailsServiceImpl extends AbstractUserDetailsService {
    private static final String[] ROLES = {"ADMIN"};

    @Resource
    private UserService userService;

    @Override
    protected String[] getRole() {
        return ROLES;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService
                .lambdaQuery()
                .eq(User::getUsername, username)
                .oneOpt()
                .orElseThrow(() -> new UsernameNotFoundException("username not exists"));
        return new BigUser(user.getId(), user.getUsername(), user.getPasswd(), getAuthorities());
    }
}
