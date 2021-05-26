package com.cqjtu.dpta.web.controller.api.open;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cqjtu.dpta.api.CreditService;
import com.cqjtu.dpta.api.DistrAppService;
import com.cqjtu.dpta.api.DistrLevelService;
import com.cqjtu.dpta.api.DistrService;
import com.cqjtu.dpta.api.DistrUserService;
import com.cqjtu.dpta.common.lang.Const;
import com.cqjtu.dpta.common.result.Result;
import com.cqjtu.dpta.common.vo.DistrVo;
import com.cqjtu.dpta.dao.entity.Distr;
import com.cqjtu.dpta.dao.entity.DistrApp;
import com.cqjtu.dpta.dao.entity.DistrLevel;
import com.cqjtu.dpta.dao.entity.DistrUser;
import com.cqjtu.dpta.web.support.ControllerUtils;
import com.cqjtu.dpta.web.support.Options;
import com.cqjtu.dpta.web.support.OptionsUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
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
@RequestMapping("/open/api")
public class DistrOpenApi {

    @Resource
    private DistrAppService distrAppService;
    @Resource
    private DistrLevelService distrLevelService;
    @Resource
    private CreditService creditService;
    @Resource
    private DistrUserService distrUserService;
    @Resource
    private DistrService distrService;

    private static final String[] COLUMNS = {"APP_ID"};

//    @GetMapping("order-state")
//    public Result orderState(){
//        OptionsUtils.list()
//    }

    @GetMapping("distr-app")
    public Result distrApp(@RequestParam(value = "keyword", required = false) String keyword,
                           HttpSession httpSession) {
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

    @GetMapping("distr")
    public Result getDistr(@RequestParam("username") Long username) {
        DistrUser distrUser = distrUserService
                .lambdaQuery()
                .eq(DistrUser::getUsername, username)
                .one();
        Distr distr = distrService
                .lambdaQuery()
                .eq(Distr::getDistrId, distrUser.getDistrId())
                .one();
        DistrVo distrVo = new DistrVo();
        BeanUtils.copyProperties(distr, distrVo);
        return Result.ok(distrVo);
    }
}
