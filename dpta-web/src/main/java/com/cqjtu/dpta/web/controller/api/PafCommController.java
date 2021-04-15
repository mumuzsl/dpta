package com.cqjtu.dpta.web.controller.api;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cqjtu.dpta.api.PafCommService;
import com.cqjtu.dpta.common.result.Result;
import com.cqjtu.dpta.dao.entity.PafComm;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
@RequestMapping("/api/data/paf-comm")
public class PafCommController {

    @Resource
    private PafCommService pafCommService;

    @GetMapping
    public IPage<PafComm> page(@PageableDefault Pageable pageable) {
        return pafCommService.page(pageable);
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
