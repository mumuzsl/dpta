package com.cqjtu.dpta.web.controller.api.admin;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cqjtu.dpta.api.DistrLevelService;
import com.cqjtu.dpta.api.DistrService;
import com.cqjtu.dpta.common.util.ResultUtils;
import com.cqjtu.dpta.dao.entity.Distr;
import com.cqjtu.dpta.dao.entity.DistrLevel;
import com.cqjtu.dpta.common.result.Result;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;


import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 分销商等级表 前端控制器
 * </p>
 *
 * @author mumu
 * @since 2021-04-13
 */
@RestController
@RequestMapping("/platform/api/distr-level")
public class DistrLevelController {

    @Resource
    private DistrLevelService distrLevelService;

    @Resource
    private DistrService distrService;

    @GetMapping("{id}")
    public Result get(@PathVariable Long id) {
        DistrLevel distrLevel = distrLevelService.getById(id);
        return Result.ok(distrLevel);
    }

    /**
     * 获取绑定了该等级规则的分销商数据
     * @param id 等级规则id
     * @param pageable
     * @return
     */
    @GetMapping("detail/{id}")
    public Result detail(@PathVariable Long id,
                         @PageableDefault Pageable pageable) {
        DistrLevel distrLevel = distrLevelService.getById(id);
        if (distrLevel == null) {
            return ResultUtils.keyError();
        }
        QueryWrapper<Distr> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("LEVEL_ID", id);
        IPage<Distr> page = distrService.page(pageable, queryWrapper);
        return Result.ok(page);
    }

    /**
     * 获取所有等级规则
     * @return
     */
    @GetMapping("all")
    public Result all() {
        List<DistrLevel> list = distrLevelService.list();
        return Result.ok(list);
    }

    @GetMapping
    public Result page(@PageableDefault Pageable pageable) {
        IPage<DistrLevel> page = distrLevelService.page(pageable);
        return Result.ok(page);
    }

    @PostMapping("modif")
    public Result modif(@RequestBody DistrLevel distrLevel) {
        boolean result = distrLevelService.updateById(distrLevel);
        return Result.judge(result);
    }

    @PostMapping("add")
    public Result add(@RequestBody DistrLevel distrLevel) {
        boolean result = distrLevelService.save(distrLevel);
        return Result.judge(result);
    }

    @PostMapping("del")
    public Result del(@RequestBody List ids) {
        boolean result = distrLevelService.removeByIds(ids);
        return Result.judge(result);
    }

}
