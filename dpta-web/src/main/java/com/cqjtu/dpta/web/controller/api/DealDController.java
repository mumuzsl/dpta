package com.cqjtu.dpta.web.controller.api;

import com.cqjtu.dpta.api.DealDService;
import com.cqjtu.dpta.common.result.Result;
import com.cqjtu.dpta.dao.entity.DealD;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/dealD")
public class DealDController {
    @Resource
    private DealDService dealDService;


    @PostMapping("modif")
    public Result modif(@RequestBody DealD dealD) {
        boolean result = dealDService.updateById(dealD);
        return Result.judge(result);
    }

    @PostMapping("add")
    public Result add(@RequestBody DealD dealD) {
        boolean result = dealDService.save(dealD);
        return Result.judge(result);
    }

    @PostMapping("del")
    public Result del(@RequestBody List ids) {
        boolean result = dealDService.removeByIds(ids);
        return Result.judge(result);
    }
}
