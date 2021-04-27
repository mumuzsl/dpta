package com.cqjtu.dpta.web.security;

import com.cqjtu.dpta.common.exception.BaseException;

/**
 * author: mumu
 * date: 2021/4/26
 */
public class UserCheckException extends BaseException {
    public UserCheckException() {}

    public UserCheckException(String message) {
        super(message);
    }
}
