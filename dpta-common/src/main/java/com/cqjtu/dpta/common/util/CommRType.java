package com.cqjtu.dpta.common.util;

/**
 * author: mumu
 * date: 2021/4/14
 */
public enum CommRType {
    RATIO("比例分润"),
    INTERNAL("区间分润");

    String message;

    CommRType(String message) {
        this.message = message;
    }

    public String message() {
        return message;
    }
}
