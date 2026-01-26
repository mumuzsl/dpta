package com.cqjtu.dpta.web.support;

import com.cqjtu.dpta.api.UserService;
import com.cqjtu.dpta.common.util.TokenUtils;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * author: mumu
 * date: 2021/4/15
 */
@Component
public class AdminInterceptor implements HandlerInterceptor {
    @Resource
    private UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = TokenUtils.getToken(request);

        return token != null && userService.getById(TokenUtils.longId(token)) != null;
    }

}
