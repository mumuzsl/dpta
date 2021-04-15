package com.cqjtu.dpta.web.controller.api;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cqjtu.dpta.api.SaleService;
import com.cqjtu.dpta.dao.entity.Sale;
import com.cqjtu.dpta.common.result.Result;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;


import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 商铺销售表 前端控制器
 * </p>
 *
 * @author mumu
 * @since 2021-04-13
 */
@RestController
@RequestMapping("/api/data/sale")
public class SaleController {

    @Resource
    private SaleService saleService;

    @GetMapping
    public IPage<Sale> page(@PageableDefault Pageable pageable) {
        return saleService.page(pageable);
    }

    @PostMapping("modif")
    public Result modif(@RequestBody Sale sale) {
        boolean result = saleService.updateById(sale);
        return Result.judge(result);
    }

    @PostMapping("add")
    public Result add(@RequestBody Sale sale) {
        boolean result = saleService.save(sale);
        return Result.judge(result);
    }

    @PostMapping("del")
    public Result del(@RequestBody List ids) {
        boolean result = saleService.removeByIds(ids);
        return Result.judge(result);
    }

}
