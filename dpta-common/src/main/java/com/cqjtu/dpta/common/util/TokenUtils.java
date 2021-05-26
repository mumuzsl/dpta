package com.cqjtu.dpta.common.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.security.web.authentication.logout.CookieClearingLogoutHandler;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * author: mumu
 * date: 2021/4/29
 */
public abstract class TokenUtils {
    private static final long EXPIRES_STEP = 3600 * 1000;
    private static final String TOKEN_NAME = "token";
    private static final String TOKEN_HEADER_NAME = "X-Token";
    private static LogoutHandler logoutHandler = new CookieClearingLogoutHandler(TOKEN_NAME);
    private static final String SECRET = "123456";
    private static final JWTVerifier DEFAULT_JWT_VERIFIER = JWT.require(Algorithm.HMAC256(SECRET)).build();

    public static Date expiresAt() {
        return new Date(System.currentTimeMillis() + EXPIRES_STEP);
    }

    public static String create(String userId, String password) {
        return JWT.create()
                .withSubject(userId)
                .withExpiresAt(expiresAt())
                .sign(Algorithm.HMAC256(SECRET));
    }

    public static boolean isTokenCookie(Cookie cookie) {
        return cookie.getName().equals(TOKEN_NAME);
    }

    public static String subject(Object token) {
        return subject(String.valueOf(token));
    }

    public static String subject(String token) {
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(SECRET)).build();
        return jwtVerifier.verify(token).getSubject();
    }

    public static DecodedJWT decode(String token) {
        return JWT.decode(token);
    }

    public static JWTVerifier verifier() {
        return DEFAULT_JWT_VERIFIER;
    }

    public static long longId(String token) {
        return Long.parseLong(subject(token));
    }

    public static long longId(Object token) {
        return Long.parseLong(subject(token));
    }

    public static <T> String create(T userId, String password) {
        return create(String.valueOf(userId), password);
    }

    public static <T> Cookie cookie(T userId, String password) {
        return cookie(create(userId, password));
    }

    public static <T> Cookie cookie(String token) {
        return new Cookie(TOKEN_NAME, token);
    }

    public static LogoutHandler tokenLogoutHandler() {
        return logoutHandler;
    }

    public static void clearHeader(HttpServletResponse response) {
        response.setHeader(TOKEN_HEADER_NAME, "");
    }

    public static String getHeader(HttpServletRequest request) {
        return request.getHeader(TOKEN_HEADER_NAME);
    }

    public static void setAttribute(HttpServletRequest request, String token) {
        request.setAttribute(TOKEN_NAME, token);
    }

    public static String getAttribute(HttpServletRequest request) {
        return String.valueOf(request.getAttribute(TOKEN_NAME));
    }

    public static String getToken(HttpServletRequest request) {
        String token = TokenUtils.getHeader(request);

        if (token == null) {
            token = request.getParameter("token");
        }

        Cookie[] cookies = null;
        if (token == null && (cookies = request.getCookies()) != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")) {
                    token = cookie.getValue();
                    break;
                }
            }
        }

        return token;
    }
}
