package com.cqjtu.dpta.web.controller.api;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.cqjtu.dpta.api.CreditService;
import com.cqjtu.dpta.api.DistrAppService;
import com.cqjtu.dpta.api.DistrLevelService;
import com.cqjtu.dpta.api.support.CrudService;
import com.cqjtu.dpta.common.lang.Const;
import com.cqjtu.dpta.common.result.Result;
import com.cqjtu.dpta.dao.entity.Credit;
import com.cqjtu.dpta.dao.entity.Distr;
import com.cqjtu.dpta.dao.entity.DistrApp;
import com.cqjtu.dpta.dao.entity.DistrLevel;
import com.cqjtu.dpta.web.support.ControllerUtils;
import com.cqjtu.dpta.web.support.Info;
import com.cqjtu.dpta.web.support.Options;
import com.cqjtu.dpta.web.support.OptionsUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 分销应用表 前端控制器
 * </p>
 *
 * @author mumu
 * @since 2021-04-13
 */
@RestController
@RequestMapping("/public/api")
public class PublicApiController {

    @Resource
    private DistrAppService distrAppService;

    @Resource
    private DistrLevelService distrLevelService;

    @Resource
    private CreditService creditService;

    private static final String[] COLUMNS = {"APP_ID"};

    @GetMapping("distr-app")
    public Result distrApp(@RequestParam(value = "keyword", required = false) String keyword) {
        List<Options<Long>> options = OptionsUtils
                .list(() -> ControllerUtils.quick(distrAppService, keyword, DistrApp::getAppNm),
                        DistrApp::getAppId,
                        DistrApp::getAppNm);
        return Result.ok(options);
    }

    @GetMapping("distr-level")
    public Result distrLevel(@RequestParam(value = "keyword", required = false) String keyword) {
        List<Options<Long>> options = OptionsUtils
                .list(() -> ControllerUtils.quick(distrLevelService, keyword, DistrLevel::getLevelNm),
                        DistrLevel::getLevelId,
                        DistrLevel::getLevelNm);
        return Result.ok(options);
    }

    @GetMapping("supp-credit-distr/{suppId:\\d+}")
    public Result pageBySupp(@PageableDefault Pageable pageable,
                             @PathVariable Long suppId,
                             @RequestParam(value = "keyword", required = false) String keyword) {
        IPage<Distr> page = creditService.pageBySuppAndState(suppId, pageable, keyword, Const.ADOPT);
        return Result.ok(page);
    }

}
