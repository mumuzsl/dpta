package com.cqjtu.dpta.web.filter;

import com.alibaba.fastjson.JSON;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.cqjtu.dpta.common.result.Result;
import com.cqjtu.dpta.common.result.ResultCodeEnum;
import com.cqjtu.dpta.common.util.TokenUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * author: mumu
 * date: 2021/4/30
 */
public class TokenFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = TokenUtils.getToken(request);

        if (token != null) {
            try {
                TokenUtils.verifier().verify(token);
                TokenUtils.setAttribute(request, token);
                filterChain.doFilter(request, response);
                return;
            } catch (JWTVerificationException e) {
                if (logger.isDebugEnabled()) {
                    logger.debug("token veify failure");
                }
            }
        }

//        response.setStatus(HttpStatus.FORBIDDEN.value());
        logger.info(request.getRequestURL());
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(JSON.toJSONString(Result.build(ResultCodeEnum.USER_NO_OR_IllEGAL_TOKEN)));
    }
}
