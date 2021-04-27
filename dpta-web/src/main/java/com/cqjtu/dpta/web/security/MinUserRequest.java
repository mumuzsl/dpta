package com.cqjtu.dpta.web.security;

import java.util.function.BiFunction;

/**
 * author: mumu
 * date: 2021/4/25
 */
public class MinUserRequest implements MinUser {

    private Object userId;
    private Object detail;

    public MinUserRequest(Object userId, Object detail) {
        this.userId = userId;
        this.detail = detail;
    }

    public static MinUserRequest of(Object userId) {
        return new MinUserRequest(userId, null);
    }

    public static MinUserRequest of(Object userId, Object detail) {
        return new MinUserRequest(userId, detail);
    }

    public <T> T convert(BiFunction<Object, Object, T> biFunction) {
        return biFunction.apply(userId, detail);
    }

    @Override
    public Object getId() {
        return userId;
    }

    @Override
    public Object getDetail() {
        return detail;
    }

}
