package com.cqjtu.dpta.common.exception;

import com.cqjtu.dpta.common.result.ResultCodeEnum;

import java.util.function.Supplier;

/**
 * author: mumu
 * date: 2021/4/23
 */
public class BadRequestException extends BaseException{
    private int code;

    public BadRequestException(String message) {
        super(message);
    }

    public BadRequestException(ResultCodeEnum code) {
        super(code.getMessage());

        this.code = code.getCode();
    }
}
