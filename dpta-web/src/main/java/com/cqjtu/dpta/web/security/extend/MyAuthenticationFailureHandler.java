package com.cqjtu.dpta.web.security.extend;

import com.alibaba.fastjson2.JSON;
import com.cqjtu.dpta.common.result.Result;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import java.io.IOException;

/**
 * author: mumu
 * date: 2021/4/22
 */
@Slf4j
public class MyAuthenticationFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        log.info("login failure");
        Result<Object> result = Result.fail();
        result.message(exception.getLocalizedMessage());
        if (exception instanceof BadCredentialsException badCredentialsException) {
            result.message(badCredentialsException.getLocalizedMessage());
        }
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType("application/json;charset=UTF-8");  // 设置字符编码为UTF-8
        response.getWriter().write(JSON.toJSONString(result));
    }
}
