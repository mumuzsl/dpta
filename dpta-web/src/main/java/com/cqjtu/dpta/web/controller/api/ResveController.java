package com.cqjtu.dpta.web.controller.api;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cqjtu.dpta.api.ResveService;
import com.cqjtu.dpta.dao.entity.Resve;
import com.cqjtu.dpta.common.result.Result;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;


import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 预备金表 前端控制器
 * </p>
 *
 * @author mumu
 * @since 2021-04-13
 */
@RestController
@RequestMapping("/api/data/resve")
public class ResveController {

    @Resource
    private ResveService resveService;

    @GetMapping
    public IPage<Resve> page(@PageableDefault Pageable pageable) {
        return resveService.page(pageable);
    }

    @PostMapping("modif")
    public Result modif(@RequestBody Resve resve) {
        boolean result = resveService.updateById(resve);
        return Result.judge(result);
    }

    @PostMapping("add")
    public Result add(@RequestBody Resve resve) {
        boolean result = resveService.save(resve);
        return Result.judge(result);
    }

    @PostMapping("del")
    public Result del(@RequestBody List ids) {
        boolean result = resveService.removeByIds(ids);
        return Result.judge(result);
    }

}
