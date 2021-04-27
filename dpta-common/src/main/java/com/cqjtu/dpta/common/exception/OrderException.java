package com.cqjtu.dpta.common.exception;

import com.cqjtu.dpta.common.result.ResultCodeEnum;

/**
 * author: mumu
 * date: 2021/4/22
 */
public class OrderException extends BadRequestException {

    public OrderException(String message) {
        super(message);
    }

}
