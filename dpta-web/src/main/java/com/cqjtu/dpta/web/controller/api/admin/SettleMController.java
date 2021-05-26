package com.cqjtu.dpta.web.controller.api.admin;

import com.cqjtu.dpta.common.result.Result;
import com.cqjtu.dpta.common.result.ResultCodeEnum;
import com.cqjtu.dpta.common.util.PageQueryUtil;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import com.cqjtu.dpta.api.support.SettleService;

import javax.annotation.Resource;
import java.util.Map;



@RestController
@RequestMapping("/platform/api/settleM")
public class SettleMController {

    @Resource
    SettleService settleService;

    @GetMapping("all")
    public Result settleM(@RequestParam Map<String, Object> params) {
        PageQueryUtil pageUtil = new PageQueryUtil(params);
        return Result.ok(settleService.getAll(pageUtil));
    }

    @GetMapping("month")
    public Result getByMonth(@RequestParam Map<String, Object> params) {
        String month = params.get("month").toString();
        if (StringUtils.isEmpty(month)) {
            return Result.build(ResultCodeEnum.PARAM_ERROR);
        }
        PageQueryUtil pageUtil = new PageQueryUtil(params);
        return Result.ok(settleService.getByMonth(pageUtil,month));
    }
}
