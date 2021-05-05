package com.cqjtu.dpta.web.controller.api.admin;

import com.cqjtu.dpta.api.StatisService;
import com.cqjtu.dpta.common.result.Result;
import com.cqjtu.dpta.common.vo.CommStatisVo;
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
@RequestMapping("/platform/api/statis")
public class PlatformStatisController {

    @Resource
    private StatisService statisService;

    /**
     * 已核销数据查询
     */
    @GetMapping("verified")
    public Result verified() {
        return Result.ok(0);
    }

    /**
     * 未核销数据查询
     */
    @GetMapping("verify")
    public Result verify() {
        return Result.ok(0);
    }

    /**
     * 总收入查询
     */
    @GetMapping("amount")
    public Result amount() {
         return Result.ok(0);
    }

    /**
     * 今日总收入、已核销收入、未核销收入查询
     */
    @GetMapping("today")
    public Result today() {
        return Result.ok(0);
    }

    /**
     * 产生收入的TOP10商品的排序，展示商品名称、单价、数量、总金额
     * @param limit top榜数量限制，默认是10
     */
    @GetMapping("top")
    public Result top(@RequestParam(value = "limit", defaultValue = "10") Integer limit) {
        List<CommStatisVo> r = statisService.topComm(limit);
        return Result.ok(r);
    }


}
