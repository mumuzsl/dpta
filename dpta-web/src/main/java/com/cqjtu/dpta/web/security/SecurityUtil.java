package com.cqjtu.dpta.web.security;

import com.cqjtu.dpta.web.support.BigUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

/**
 * author: mumu
 * date: 2021/4/26
 */
public abstract class SecurityUtil {
    public static Optional<BigUser> bigUserOpt() {
        SecurityContext context = SecurityContextHolder.getContext();
        return Optional
                .of(context)
                .map(SecurityContext::getAuthentication)
                .map(Authentication::getPrincipal)
                .filter(BigUser.class::isInstance)
                .map(BigUser.class::cast);
    }

    public static Optional<MinUser> minUserOpt() {
        return bigUserOpt().map(MinUserRequest::of);
    }
}
