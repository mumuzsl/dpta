package com.cqjtu.dpta.web.controller.api;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cqjtu.dpta.api.DistrUserService;
import com.cqjtu.dpta.dao.entity.DistrUser;
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
 * @since 2021-04-15
 */
@RestController
@RequestMapping("/api/data/distr-user")
public class DistrUserController {

    @Resource
    private DistrUserService distrUserService;

    @GetMapping
    public Result page(@PageableDefault Pageable pageable) {
        IPage<DistrUser> page = distrUserService.page(pageable);
        return Result.ok(page);
    }

    @PostMapping("modif")
    public Result modif(@RequestBody DistrUser distrUser) {
        boolean result = distrUserService.updateById(distrUser);
        return Result.judge(result);
    }

    @PostMapping("add")
    public Result add(@RequestBody DistrUser distrUser) {
        boolean result = distrUserService.save(distrUser);
        return Result.judge(result);
    }

    @PostMapping("del")
    public Result del(@RequestBody List ids) {
        boolean result = distrUserService.removeByIds(ids);
        return Result.judge(result);
    }

}
