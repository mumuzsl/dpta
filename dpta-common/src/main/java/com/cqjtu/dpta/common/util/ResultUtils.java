package com.cqjtu.dpta.common.util;


import com.cqjtu.dpta.common.result.Result;
import com.cqjtu.dpta.common.result.ResultCodeEnum;

/**
 * author: mumu
 * date: 2021/4/13
 */
public final class ResultUtils {
    private ResultUtils() {}

    private interface CommonResult {
        Result<?> KEY_ERROR = Result.build(ResultCodeEnum.KEY_ERROR);
    }

    public static Result<?> keyError() {
        return CommonResult.KEY_ERROR;
    }


}
