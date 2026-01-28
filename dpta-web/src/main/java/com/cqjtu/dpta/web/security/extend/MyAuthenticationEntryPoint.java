package com.cqjtu.dpta.web.security.extend;

import com.alibaba.fastjson2.JSON;
import com.cqjtu.dpta.common.result.Result;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.server.resource.InvalidBearerTokenException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;

/**
 * @author: mumu
 * @date: 2026/1/26 10:56
 */
public class MyAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        Result<Object> result = Result.fail();
        result.message(authException.getLocalizedMessage());
        if (authException instanceof InsufficientAuthenticationException ||
                authException instanceof InvalidBearerTokenException e) {
            result.message("认证信息无效");
        }
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType("application/json;charset=UTF-8");  // 设置字符编码为UTF-8
        response.getWriter().write(JSON.toJSONString(result));
    }
}
