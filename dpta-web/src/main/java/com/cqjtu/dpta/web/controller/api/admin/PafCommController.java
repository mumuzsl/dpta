package com.cqjtu.dpta.web.controller.api.admin;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cqjtu.dpta.api.PafCommService;
import com.cqjtu.dpta.dao.entity.PafComm;
import com.cqjtu.dpta.common.result.Result;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;


import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 平台-商品表 前端控制器
 * </p>
 *
 * @author mumu
 * @since 2021-04-13
 */
@RestController
@RequestMapping("/platform/api/paf-comm")
public class PafCommController {

    @Resource
    private PafCommService pafCommService;

    private static final String[] COLUMNS = {"COMM_NM", "TYPE", "STATE"};

    /**
     * 模糊搜索
     * @param pageable
     * @param keyword
     * @param type     商品类型
     * @return
     */
    @GetMapping("search")
    public Result search(@PageableDefault Pageable pageable,
                         @RequestParam("keyword") String keyword,
                         @RequestParam(value = "type", required = false, defaultValue = "") String type) {
        QueryWrapper<PafComm> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(COLUMNS[0], keyword);
        if (!StringUtils.isBlank(type)) {
            queryWrapper.like(COLUMNS[1], type);
        }
        IPage<PafComm> page = pafCommService.page(pageable, queryWrapper);
        return Result.ok(page);
    }

    @GetMapping
    public Result page(@PageableDefault Pageable pageable) {
        IPage<PafComm> page = pafCommService.page(pageable);
        return Result.ok(page);
    }

    @PostMapping("modif")
    public Result modif(@RequestBody PafComm pafComm) {
        boolean result = pafCommService.updateById(pafComm);
        return Result.judge(result);
    }

    @PostMapping("add")
    public Result add(@RequestBody PafComm pafComm) {
        boolean result = pafCommService.save(pafComm);
        return Result.judge(result);
    }

    @PostMapping("del")
    public Result del(@RequestBody List ids) {
        boolean result = pafCommService.removeByIds(ids);
        return Result.judge(result);
    }

}
