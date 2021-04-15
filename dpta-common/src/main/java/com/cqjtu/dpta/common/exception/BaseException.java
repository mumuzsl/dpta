package com.cqjtu.dpta.common.exception;

/**
 * author: mumu
 * date: 2021/4/13
 */
public abstract class BaseException extends RuntimeException {
    public BaseException(String message) {
        super(message);
    }
}
