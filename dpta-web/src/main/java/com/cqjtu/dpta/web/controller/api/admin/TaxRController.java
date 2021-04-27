package com.cqjtu.dpta.web.controller.api.admin;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cqjtu.dpta.api.TaxRService;
import com.cqjtu.dpta.dao.entity.TaxR;
import com.cqjtu.dpta.common.result.Result;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;


import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 税费规则表 前端控制器
 * </p>
 *
 * @author mumu
 * @since 2021-04-13
 */
@RestController
@RequestMapping("/platform/api/tax-rule")
public class TaxRController {

    @Resource
    private TaxRService taxRService;

    @GetMapping
    public Result page(@PageableDefault Pageable pageable) {
        IPage<TaxR> page = taxRService.page(pageable);
        return Result.ok(page);
    }

    @PostMapping("modif")
    public Result modif(@RequestBody TaxR taxR) {
        boolean result = taxRService.updateById(taxR);
        return Result.judge(result);
    }

    @PostMapping("add")
    public Result add(@RequestBody TaxR taxR) {
        boolean result = taxRService.save(taxR);
        return Result.judge(result);
    }

    @PostMapping("del")
    public Result del(@RequestBody List ids) {
        boolean result = taxRService.removeByIds(ids);
        return Result.judge(result);
    }

}
