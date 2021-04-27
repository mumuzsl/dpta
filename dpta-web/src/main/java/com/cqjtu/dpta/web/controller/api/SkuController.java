package com.cqjtu.dpta.web.controller.api;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cqjtu.dpta.api.SkuService;
import com.cqjtu.dpta.dao.entity.Sku;
import com.cqjtu.dpta.common.result.Result;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;


import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author mumu
 * @since 2021-04-23
 */
@RestController
@RequestMapping("/api/data/sku")
public class SkuController {

    @Resource
    private SkuService skuService;

    @GetMapping
    public Result page(@PageableDefault Pageable pageable) {
        IPage<Sku> page = skuService.page(pageable);
        return Result.ok(page);
    }

    @PostMapping("modif")
    public Result modif(@RequestBody Sku sku) {
        boolean result = skuService.updateById(sku);
        return Result.judge(result);
    }

    @PostMapping("add")
    public Result add(@RequestBody Sku sku) {
        boolean result = skuService.save(sku);
        return Result.judge(result);
    }

    @PostMapping("del")
    public Result del(@RequestBody List ids) {
        boolean result = skuService.removeByIds(ids);
        return Result.judge(result);
    }

}
