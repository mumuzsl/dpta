package com.cqjtu.dpta.web.filter;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cqjtu.dpta.common.result.Result;
import com.cqjtu.dpta.dao.entity.Order;
import com.cqjtu.dpta.web.security.TokenUtils;
import org.apache.catalina.Host;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Enumeration;
import java.util.Locale;

/**
 * author: mumu
 * date: 2021/4/30
 */
public class TokenFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = TokenUtils.getHeader(request);

        if (StringUtils.isBlank(token)) {
            token = request.getParameter("token");
        }

        if (StringUtils.isNotBlank(token)) {
            try {
                TokenUtils.verifier().verify(token);
                TokenUtils.setAttribute(request, token);
                filterChain.doFilter(request, response);
            } catch (JWTVerificationException e) {
                if (logger.isDebugEnabled()) {
                    logger.debug("token veify failure");
                }
            }
        }

        response.setStatus(HttpStatus.FORBIDDEN.value());
    }
}
