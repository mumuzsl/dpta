package com.cqjtu.dpta.web.support;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * author: mumu
 * date: 2021/4/22
 */
public class UserIdAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
        Object credentials = authentication.getCredentials();
        Object principal = authentication.getPrincipal();
        Object details = authentication.getDetails();

        System.out.println("==================================");

        super.onAuthenticationSuccess(request, response, authentication);
    }

}
