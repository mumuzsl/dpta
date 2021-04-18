package com.cqjtu.dpta.web.controller.api.admin;

import com.cqjtu.dpta.api.StatisService;
import com.cqjtu.dpta.common.result.Result;
import com.cqjtu.dpta.common.vo.CommVo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * author: mumu
 * date: 2021/4/13
 */
@RestController
@RequestMapping("/api/statisz")
public class StatisController {

    @Resource
    private StatisService statisService;

    @GetMapping("verified")
    public Result verified() {
        return Result.ok(0);
    }

    @GetMapping("verify")
    public Result verify() {
        return Result.ok(0);
    }

    @GetMapping("amount")
    public Result amount() {
        return Result.ok(0);
    }

    @GetMapping("today")
    public Result today() {
        return Result.ok(0);
    }

    @GetMapping("top")
    public Result top(@RequestParam(value = "limit", defaultValue = "10") Integer limit) {
        List<CommVo> r = statisService.topComm(limit);
        return Result.ok(r);
    }


}
