package com.cqjtu.dpta.web.controller.api;

import com.cqjtu.dpta.api.StatisService;
import com.cqjtu.dpta.common.result.Result;
import com.cqjtu.dpta.common.result.ResultCodeEnum;
import com.cqjtu.dpta.common.vo.CommStatisVo;
import com.cqjtu.dpta.common.vo.StatisVo;
import com.cqjtu.dpta.common.vo.XyVo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * author: mumu
 * date: 2021/4/13
 */
@RestController
@RequestMapping("/distr/api/statis")
public class StatisController {

    @Resource
    private StatisService statisService;

    private static List<Object> ydata = new ArrayList<>(7);

    static {
        ydata.add(168);
        ydata.add(169);
        ydata.add(160);
        ydata.add(159);
        ydata.add(170);
        ydata.add(155);
        ydata.add(166);
    }

    @GetMapping("recent/person-time")
    public Result personTime(@RequestParam(value = "day", required = false, defaultValue = "7") int day) {
        Result<XyVo> result = Result.ok();
        if (day > 7) {
            result.setMessage(ResultCodeEnum.DATA_BIG_DAY_ERROR.getMessage());
        }
        XyVo xyVo = XyVo.of(day);
        LocalDateTime now = LocalDateTime.now();
        for (int i = 0; i < day; i++) {
            xyVo.getXdata().add(now.minusDays(i + 1).toLocalDate());
        }
        List<Object> newYData = new ArrayList<>(day);
        for (Object o : ydata) {
            newYData.add((int) o * 3);
        }
        xyVo.setYdata(newYData);
        result.setData(xyVo);
        return result;
    }

    @GetMapping("recent/person-number")
    public Result recent(@RequestParam(value = "day", required = false, defaultValue = "7") int day) {
        Result<XyVo> result = Result.ok();
        if (day > 7) {
            result.setMessage(ResultCodeEnum.DATA_BIG_DAY_ERROR.getMessage());
        }
        XyVo xyVo = XyVo.of(day);
        LocalDateTime now = LocalDateTime.now();
        for (int i = 0; i < day; i++) {
            xyVo.getXdata().add(now.minusDays(i + 1).toLocalDate());
        }
        xyVo.setYdata(ydata);
        result.setData(xyVo);
        return result;
    }

    @GetMapping("all")
    public Result all() {
        StatisVo statisVo = new StatisVo();
        return Result.ok(statisVo);
    }

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
     *
     * @param limit top榜数量限制，默认是10
     */
    @GetMapping("top")
    public Result top(@RequestParam(value = "limit", defaultValue = "10") Integer limit) {
        List<CommStatisVo> r = statisService.topComm(limit);
        return Result.ok(r);
    }


}
