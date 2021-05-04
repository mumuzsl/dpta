package com.cqjtu.dpta.web.security;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.LocalDateTimeUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.cqjtu.dpta.dao.entity.User;
import com.cqjtu.dpta.web.support.BigUser;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.web.authentication.logout.CookieClearingLogoutHandler;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

/**
 * author: mumu
 * date: 2021/4/29
 */
public abstract class TokenUtils {
    private static final long EXPIRES_STEP = DateUtils.MILLIS_PER_HOUR;
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


    public static String create(User user) {
        return create(user.getId().toString(), user.getPasswd());
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


    public static void main(String[] args) {
//        Instant instant = TokenUtils.decode(token).getExpiresAt().toInstant();
//        ZoneId zoneId = ZoneId.systemDefault();
//        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, zoneId);
//        String s = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss", Locale.CHINA).format(localDateTime);
//        System.out.println(s);
//        String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxMDAyIiwiZXhwIjoxNjIzNTQ2MjI0fQ.MGED68hkVIWqdpJxfIxCazDC0nk0VqJrrDapOZuPlnc";
//        Date expiresAt = TokenUtils.decode(token).getExpiresAt();
//        String format = DateUtil.format(expiresAt, "yyyy-MM-dd HH:mm:ss");
//        System.out.println(format);
//        System.out.println(DateUtil.formatDateTime(expiresAt()));
//        String s = create(1002, null);
//        System.out.println(s);
//        System.out.println(s);
        String ss = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxMDAyIiwiZXhwIjoxNjE5OTQ3NDMwfQ.R3EevKqyuZDI2yg5IW-iOKGQqEz2vRisU218H4MJ_us";
        subject(ss);
    }
}
