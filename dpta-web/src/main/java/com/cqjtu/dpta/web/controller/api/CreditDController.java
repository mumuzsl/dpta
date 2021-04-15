package com.cqjtu.dpta.web.controller.api;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cqjtu.dpta.api.CreditDService;
import com.cqjtu.dpta.dao.entity.CreditD;
import com.cqjtu.dpta.common.result.Result;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;


import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 授信明细表 前端控制器
 * </p>
 *
 * @author mumu
 * @since 2021-04-13
 */
@RestController
@RequestMapping("/api/credit-d")
public class CreditDController {

    @Resource
    private CreditDService creditDService;

    @GetMapping
    public IPage<CreditD> page(@PageableDefault Pageable pageable) {
        return creditDService.page(pageable);
    }

    @PostMapping("modif")
    public Result modif(@RequestBody CreditD creditD) {
        boolean result = creditDService.updateById(creditD);
        return Result.judge(result);
    }

    @PostMapping("add")
    public Result add(@RequestBody CreditD creditD) {
        boolean result = creditDService.save(creditD);
        return Result.judge(result);
    }

    @PostMapping("del")
    public Result del(@RequestBody List ids) {
        boolean result = creditDService.removeByIds(ids);
        return Result.judge(result);
    }

}
