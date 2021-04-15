package com.cqjtu.dpta.common.redis;


import com.cqjtu.dpta.common.exception.BaseException;

/**
 * Redis不可用异常类
 */
public class RedisDisabledException extends BaseException {

    public RedisDisabledException(String message) {
        super(message);
    }

}
