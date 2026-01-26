package com.cqjtu.dpta.web.security.extend;

import com.cqjtu.dpta.api.UserService;
import com.cqjtu.dpta.dao.entity.User;
import com.cqjtu.dpta.web.support.BigUser;
import jakarta.annotation.Resource;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Collections;

/**
 * @author: mumu
 * @date: 2026/1/25 16:10
 */
// @Component
public class MyUserDetailsService implements UserDetailsService {
    @Resource
    UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.lambdaQuery()
                .eq(User::getUsername, username)
                .one();
        if (user == null) {
            throw new UsernameNotFoundException("username not exists");
        }
        return new BigUser(user.getId(), user.getUsername(), user.getPasswd(), Collections.emptyList());
    }
}
