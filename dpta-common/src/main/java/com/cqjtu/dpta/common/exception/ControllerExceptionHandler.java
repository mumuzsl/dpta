package com.cqjtu.dpta.common.exception;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.cqjtu.dpta.common.redis.RedisDisabledException;
import com.cqjtu.dpta.common.result.Result;
import com.cqjtu.dpta.common.result.ResultCodeEnum;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

/**
 * author: mumu
 * date: 2021/4/13
 */
@ControllerAdvice
public class ControllerExceptionHandler {
    @ExceptionHandler(RedisDisabledException.class)
    @ResponseBody
    public Result handleRedisDisabledException(RedisDisabledException e) {
        return Result.fail(e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result handleException(Exception e) {
        e.printStackTrace();
        return Result.build(ResultCodeEnum.SERVICE_ERROR);
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

    @ExceptionHandler(JWTVerificationException.class)
    @ResponseBody
    public Result handleJWTVerificationException(JWTVerificationException e) {
        return Result.fail("token无效");
    }

    @ExceptionHandler(NoTokenException.class)
    @ResponseBody
    public Result handleNoTokenException(NoTokenException e) {
        e.printStackTrace();
        return Result.fail("没有token");
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseBody
    public Result handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
        return Result.fail("方法参数类不匹配");
    }

}
