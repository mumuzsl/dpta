package com.cqjtu.dpta.web.service;

import com.cqjtu.dpta.api.DistrService;
import com.cqjtu.dpta.api.DistrUserService;
import com.cqjtu.dpta.dao.entity.DistrUser;
import com.cqjtu.dpta.web.support.BigUser;
import jakarta.annotation.Resource;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;

/**
 * author: mumu
 * date: 2021/3/16
 */
@Service
public class DistrUserDetailsServiceImpl extends AbstractUserDetailsService {
    @Resource
    private DistrUserService distrUserService;
    @Resource
    private DistrService distrService;

    private static final String[] ROLES = {"DISTR"};

    @Override
    protected String[] getRole() {
        return ROLES;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        DistrUser distrUser = distrUserService
                .lambdaQuery()
                .eq(DistrUser::getUsername, username)
                .oneOpt()
                .orElseThrow(() -> new UsernameNotFoundException("username not exists"));
        distrUser.setLastLoginTime(LocalDateTime.now());
        distrUserService.updateById(distrUser);
        return new BigUser(distrUser.getDistrId(), distrUser.getUsername(), distrUser.getPassword(), Collections.emptyList());
    }

    @Override
    public boolean support(String clientId, String grantType) {
        return "distr_user".equals(clientId) && "password".equals(grantType);
    }

}

