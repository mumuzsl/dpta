package com.cqjtu.dpta.web.controller.api.admin;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cqjtu.dpta.api.RefundRService;
import com.cqjtu.dpta.common.lang.Range;
import com.cqjtu.dpta.common.util.DptaUtils;
import com.cqjtu.dpta.common.util.ResultUtils;
import com.cqjtu.dpta.dao.entity.CommR;
import com.cqjtu.dpta.dao.entity.RefundR;
import com.cqjtu.dpta.common.result.Result;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;


import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 退款规则表 前端控制器
 * </p>
 *
 * @author mumu
 * @since 2021-04-13
 */
@RestController
@RequestMapping("/api/refund-rule")
public class RefundRController {

    @Resource
    private RefundRService refundRService;

    private static final String[] COLUMNS = {"REFUND_NM", "TYPE", "STATE"};

    @GetMapping("sort")
    public Result sort(@PageableDefault Pageable pageable) {
        IPage<RefundR> page = refundRService.bindSort(pageable);
        return Result.ok(page);
    }

    @GetMapping("search")
    public Result search(@PageableDefault Pageable pageable,
                         @RequestParam("keyword") String keyword,
                         @RequestParam(value = "option", required = false, defaultValue = "0") Integer option) {
        QueryWrapper<RefundR> queryWrapper = new QueryWrapper<>();
        if (!DptaUtils.in02(option)) {
            return ResultUtils.keyError();
        }
        queryWrapper.like(COLUMNS[option], keyword);
        IPage<RefundR> page = refundRService.page(pageable, queryWrapper);
        return Result.ok(page);
    }

    @GetMapping("all")
    public Result all() {
        List<RefundR> list = refundRService.list();
        return Result.ok(list);
    }

    @GetMapping
    public Result page(@PageableDefault Pageable pageable) {
        IPage<RefundR> page = refundRService.page(pageable);
        return Result.ok(page);
    }

    @PostMapping("modif")
    public Result modif(@RequestBody RefundR refundR) {
        boolean result = refundRService.updateById(refundR);
        return Result.judge(result);
    }

    @PostMapping("add")
    public Result add(@RequestBody RefundR refundR) {
        boolean result = refundRService.save(refundR);
        return Result.judge(result);
    }

    @PostMapping("del")
    public Result del(@RequestBody List ids) {
        boolean result = refundRService.removeByIds(ids);
        return Result.judge(result);
    }

}
