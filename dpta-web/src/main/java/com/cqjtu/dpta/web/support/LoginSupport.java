package com.cqjtu.dpta.web.support;

import com.cqjtu.dpta.common.result.Result;
import com.cqjtu.dpta.common.result.ResultCodeEnum;
import com.cqjtu.dpta.common.util.TokenUtils;
import com.cqjtu.dpta.common.web.Info;
import com.cqjtu.dpta.common.web.LoginParam;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.function.Consumer;

/**
 * author: mumu
 * date: 2021/5/23
 */
public abstract class LoginSupport {
    @Resource
    private PasswordEncoder passwordEncoder;

    public Result postLogout(HttpServletResponse response) {
        TokenUtils.clearHeader(response);
        return Result.ok();
    }

    public Result postLogin(LoginParam loginParam, Consumer<String> consumer) {
        String username = loginParam.getUsername();
        String password = loginParam.getPassword();

        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
            return Result.build(ResultCodeEnum.USER_LOGIN_PARAM_ERROR);
        }

        BigUser user = null;
        try {
            user = (BigUser) userDetailsService().loadUserByUsername(username);
            if (!passwordEncoder.matches(password, user.getPassword())) {
                return Result.build(ResultCodeEnum.USER_PASSWORD_NOT_MATCH);
            }
            String token = TokenUtils.create(user.getId(), password);
            Info info = new Info();
            info.setToken(token);
            consumer.accept(token);
            return Result.ok(info);
        } catch (UsernameNotFoundException e) {
            return Result.build(ResultCodeEnum.USER_NOT_FOUNT_USER);
        }
    }

    public Result loginedAddCookie(LoginParam loginParam,
                                   HttpServletResponse response) {
        return postLogin(loginParam, token -> {
            Cookie cookie = TokenUtils.cookie(token);
            response.addCookie(cookie);
        });
    }

    public abstract UserDetailsService userDetailsService();

    public PasswordEncoder passwordEncoder() {
        return passwordEncoder;
    }
}
