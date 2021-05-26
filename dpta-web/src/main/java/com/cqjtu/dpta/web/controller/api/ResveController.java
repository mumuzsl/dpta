package com.cqjtu.dpta.web.controller.api;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cqjtu.dpta.api.ResveDService;
import com.cqjtu.dpta.api.ResveService;
import com.cqjtu.dpta.common.lang.Const;
import com.cqjtu.dpta.common.result.Result;
import com.cqjtu.dpta.common.web.Info;
import com.cqjtu.dpta.dao.entity.ResveD;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Map;

/**
 * <p>
 * 预备金表 前端控制器
 * </p>
 *
 * @author mumu
 * @since 2021-04-13
 */
@RestController
@RequestMapping("/distr/api/resve")
public class ResveController {

    @Resource
    private ResveService resveService;

    @Resource
    ResveDService resveDService;

    @PostMapping("add")
    public Result add(@RequestBody Map<String, String> map,
                      Info info) {
        return useResve(info.id(), map.get("pay_value"), Const.RECHARGE);
    }

    private Result useResve(Long distrId, String value, int type) {
        try {
            BigDecimal v = BigDecimal.valueOf(Double.parseDouble(value));
            Boolean b = resveService.useResve(distrId, v, type);
            return Result.judge(b);
        } catch (NumberFormatException | NullPointerException e) {
            return Result.fail();
        }
    }


    @GetMapping("/recharge")
    public Result recharge(@PageableDefault Pageable pageable, Info info) {
        Page<ResveD> page = resveDService
                .lambdaQuery()
                .eq(ResveD::getDistrId, info.id())
                .eq(ResveD::getType, Const.RECHARGE)
                .page(resveDService.toPage(pageable));
        return Result.ok(page);
    }

    @GetMapping
    public Result page(@PageableDefault Pageable pageable, Info info) {
        Page<ResveD> page = resveDService
                .lambdaQuery()
                .eq(ResveD::getDistrId, info.id())
                .page(resveDService.toPage(pageable));
        return Result.ok(page);
    }

    /**
     * 根据分销商编码获得分销商的预备金明细
     *
     * @param pageable
     * @param id       分销商编码
     * @return
     */
//    @GetMapping("detail/{id}")
//    public Result detail(@PageableDefault Pageable pageable,
//                         @PathVariable Long id) {
//        Resve resve = resveService.getById(id);
//        if (resve == null) {
//            return ResultUtils.keyError();
//        }
//        QueryWrapper<ResveD> queryWrapper = new QueryWrapper();
//        queryWrapper.eq("DISTR_ID", id);
//        IPage<ResveD> page = resveDService.page(pageable, queryWrapper);
//        return Result.ok(page);
//    }

//    @GetMapping
//    public IPage<Resve> page(@PageableDefault Pageable pageable) {
//        return resveService.page(pageable);
//    }

//    @PostMapping("modif")
//    public Result modif(@RequestBody Resve resve) {
//        boolean result = resveService.updateById(resve);
//        return Result.judge(result);
//    }
//
//    @PostMapping("add")
//    public Result add(@RequestBody Resve resve) {
//        boolean result = resveService.save(resve);
//        return Result.judge(result);
//    }
//
//    @PostMapping("del")
//    public Result del(@RequestBody List ids) {
//        boolean result = resveService.removeByIds(ids);
//        return Result.judge(result);
//    }

}
