package com.cqjtu.dpta.web.security;

import java.util.function.BiFunction;

/**
 * author: mumu
 * date: 2021/4/25
 */
public interface MinUser {

    Object getId();

    Object getDetail();

    default boolean hasDetail() {
        return getDetail() != null;
    }
}

