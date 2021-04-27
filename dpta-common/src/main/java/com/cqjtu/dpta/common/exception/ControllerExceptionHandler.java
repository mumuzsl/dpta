package com.cqjtu.dpta.common.exception;

import com.cqjtu.dpta.common.redis.RedisDisabledException;
import com.cqjtu.dpta.common.result.Result;
import com.cqjtu.dpta.common.result.ResultCodeEnum;
import com.cqjtu.dpta.common.util.ResultUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * author: mumu
 * date: 2021/4/13
 */
@ControllerAdvice
//@RestControllerAdvice({"com.cqjtu.dpta.controller.rest"})
public class ControllerExceptionHandler {
    @ExceptionHandler(RedisDisabledException.class)
    @ResponseBody
    public Result handleRedisDisabledException(RedisDisabledException e) {
        return Result.fail(e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result handleException(Exception e) {
        return Result.fail(e.getMessage());
    }

    @ExceptionHandler(OptionException.class)
    @ResponseBody
    public Result handleOptionException(OptionException e) {
        return Result.fail(e.getMessage());
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseBody
    public Result handleOrderException(BadRequestException e) {
        return Result.fail(e.getMessage());
    }
}
