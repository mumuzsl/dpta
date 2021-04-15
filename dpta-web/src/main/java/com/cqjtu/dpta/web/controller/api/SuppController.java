package com.cqjtu.dpta.web.controller.api;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cqjtu.dpta.api.SuppService;
import com.cqjtu.dpta.dao.entity.Supp;
import com.cqjtu.dpta.common.result.Result;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;


import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 供应商表 前端控制器
 * </p>
 *
 * @author mumu
 * @since 2021-04-13
 */
@RestController
@RequestMapping("/api/data/supp")
public class SuppController {

    @Resource
    private SuppService suppService;

    @GetMapping
    public IPage<Supp> page(@PageableDefault Pageable pageable) {
        return suppService.page(pageable);
    }

    @PostMapping("modif")
    public Result modif(@RequestBody Supp supp) {
        boolean result = suppService.updateById(supp);
        return Result.judge(result);
    }

    @PostMapping("add")
    public Result add(@RequestBody Supp supp) {
        boolean result = suppService.save(supp);
        return Result.judge(result);
    }

    @PostMapping("del")
    public Result del(@RequestBody List ids) {
        boolean result = suppService.removeByIds(ids);
        return Result.judge(result);
    }

}
