package com.cqjtu.dpta.web.controller.api.admin;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cqjtu.dpta.api.CreditDService;
import com.cqjtu.dpta.api.CreditService;
import com.cqjtu.dpta.api.DealService;
import com.cqjtu.dpta.common.lang.Const;
import com.cqjtu.dpta.common.result.ResultCodeEnum;
import com.cqjtu.dpta.common.util.DptaUtils;
import com.cqjtu.dpta.common.util.PageQueryUtil;
import com.cqjtu.dpta.common.util.ResultUtils;
import com.cqjtu.dpta.dao.entity.Credit;
import com.cqjtu.dpta.common.result.Result;
import com.cqjtu.dpta.dao.entity.CreditD;
import com.cqjtu.dpta.dao.entity.Deal;
import com.cqjtu.dpta.dao.entity.RefundR;
import io.swagger.annotations.ApiParam;
import net.bytebuddy.asm.Advice;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;


import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 授信表 前端控制器
 * </p>
 *
 * @author mumu
 * @since 2021-04-13
 */
@RestController
@RequestMapping("/platform/api/credit")
public class PlatformCreditController {

    @Resource
    private CreditService creditService;

    @Resource
    private DealService dealService;

    @Resource
    private CreditDService creditDService;

    private static final String[] COLUMNS = {"SUPP_ID", "DISTR_ID", "STATE", "CREDIT_ID"};

    /**
     * @param pageable
     * @param id
     * @return
     */
    @GetMapping("detail/{id}")
    public Result detail(@PageableDefault Pageable pageable,
                         @PathVariable Long id) {
        Credit credit = creditService.getById(id);
        if (credit == null) {
            return ResultUtils.keyError();
        }
        QueryWrapper<CreditD> queryWrapper = new QueryWrapper();
        queryWrapper.eq(COLUMNS[3], id);
        IPage<CreditD> page = creditDService.page(pageable, queryWrapper);
        return Result.ok(page);
    }

    /**
     * 根据授信id按页获取相应的交易明细
     * @param pageable
     * @param id
     * @return
     */
    @GetMapping("deal/{id}")
    public Result deal(@PageableDefault Pageable pageable,
                       @PathVariable Long id) {
        Credit credit = creditService.getById(id);
        if (credit == null) {
            return ResultUtils.keyError();
        }
        IPage<Deal> page = dealService.pageBySuppAndDistr(pageable, credit.getSuppId(), credit.getDistrId());
        return Result.ok(page);
    }

    /**
     * 根据授信id修改状态
     * @param id
     * @param state
     * @return
     */
    @GetMapping("state/{id}/{state}")
    public Result state(@PathVariable Long id,
                        @PathVariable Integer state) {
        Credit credit = creditService.getById(id);
        if (DptaUtils.in03(state)) {
            return ResultUtils.keyError();
        }
        credit.setState(state);
        boolean b = creditService.updateById(credit);
        return Result.ok(b);
    }

    /**
     * 根据分销商名称或供应商名称模糊搜索
     * @param pageable
     * @param keyword
     * @param option 0表示按供应商名称模糊搜索，1表示按分销商名称模糊搜索
     * @return
     */
    @GetMapping("applied/search")
    public Result applied(@PageableDefault Pageable pageable,
                          @RequestParam("keyword") String keyword,
                          @RequestParam(value = "option", required = false, defaultValue = "0") Integer option) {
        if (DptaUtils.in01(option)) {
            return ResultUtils.keyError();
        }
        IPage<Credit> page = null;
        if (option == 0) {
            page = creditService.applyBySuppNm(pageable, keyword, 1);
        } else {
            page = creditService.applyByDistrNm(pageable, keyword, 1);
        }
        return Result.ok(page);
    }

    /**
     * 根据供应商名称搜索授信申请，按页返回数据
     * @param pageable
     * @param keyword
     * @return
     */
    @GetMapping("apply/search")
    public Result apply(@PageableDefault Pageable pageable,
                        @RequestParam("keyword") String keyword) {
        IPage<Credit> page = creditService.applyBySuppNm(pageable, keyword, 0);
        return Result.ok(page);
    }

    /**
     * 分页获取授信申请
     * @param pageable
     * @return
     */
    @GetMapping("apply")
    public Result apply(@PageableDefault Pageable pageable) {
        return byState(pageable, 0);
    }

    /**
     * 分页获取已通过授信
     *
     * @param pageable
     * @return
     */
    @GetMapping("applied")
    public Result applied(@PageableDefault Pageable pageable) {
        return byState(pageable, 1);
    }

    public Result byState(Pageable pageable, Integer state) {
        QueryWrapper<Credit> queryWrapper = new QueryWrapper<Credit>();
        queryWrapper.like(COLUMNS[2], state);
        IPage<Credit> page = creditService.page(pageable, queryWrapper);
        return Result.ok(page);
    }

    @GetMapping
    public IPage<Credit> page(@PageableDefault Pageable pageable) {
        return creditService.page(pageable);
    }

    @PostMapping("modif")
    public Result modif(@RequestBody Credit credit) {
        boolean result = creditService.updateById(credit);
        return Result.judge(result);
    }

    @PostMapping("add")
    public Result add(@RequestBody Credit credit) {
        boolean result = creditService.save(credit);
        return Result.judge(result);
    }

    @PostMapping("del")
    public Result del(@RequestBody List ids) {
        boolean result = creditService.removeByIds(ids);
        return Result.judge(result);
    }

    @GetMapping("findByNm")
    public Result findByNm(@RequestParam Map<String, Object> params) {
        String name = params.get("name").toString();
        if(name.equals("*"))
            name = "";
        PageQueryUtil pageUtil = new PageQueryUtil(params);
        return Result.ok(creditService.findByName(pageUtil,name));
    }

    @GetMapping("findByNm1")
    public Result findByNm1(@RequestParam Map<String, Object> params) {
        String name = params.get("name").toString();
        if(name.equals("*"))
            name = "";
        PageQueryUtil pageUtil = new PageQueryUtil(params);
        return Result.ok(creditService.findByName1(pageUtil,name));
    }

    @PostMapping("enable")
    public Result enable(@RequestBody List<Long> ids) {
        List<Credit> list = new ArrayList<>();
        for (Long id : ids) {
            Credit credit = creditService.getById(id);
            credit.setState(Const.ADOPT);
            list.add(credit);
        }
        boolean b = creditService.updateBatchById(list);
        return Result.judge(b);
    }
    @PostMapping("disable")
    public Result disable(@RequestBody List<Long> ids) {
        List<Credit> list = new ArrayList<>();
        for (Long id : ids) {
            Credit credit = creditService.getById(id);
            credit.setState(Const.FORBIDDEN);
            list.add(credit);
        }
        boolean b = creditService.updateBatchById(list);
        return Result.judge(b);
    }

    @PostMapping("reject")
    public Result reject(@RequestBody List<Long> ids) {
        List<Credit> list = new ArrayList<>();
        for (Long id : ids) {
            Credit credit = creditService.getById(id);
            credit.setState(Const.REJECT);
            list.add(credit);
        }
        boolean b = creditService.updateBatchById(list);
        return Result.judge(b);
    }

    @GetMapping("viewCreditR/{id}")
    public Result viewCredit(@RequestParam Map<String, Object> params,
                              @PathVariable Long id){
        if (id == null){
            return Result.build(ResultCodeEnum.PARAM_ERROR);
        }
        PageQueryUtil pageUtil = new PageQueryUtil(params);
        return Result.ok(creditDService.getRecords(pageUtil,id));
    }

    @GetMapping("viewCreditR1/{id}")
    public Result viewCredit1(@RequestParam Map<String, Object> params,
                              @PathVariable Long id){
        Credit credit = creditService.getById(id);
        if (credit == null){
            return Result.build(ResultCodeEnum.PARAM_ERROR);
        }
        PageQueryUtil pageUtil = new PageQueryUtil(params);
        return Result.ok(creditService.getRecords(pageUtil,credit.getSuppId(),credit.getDistrId()));
    }
}
