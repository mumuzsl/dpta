package com.cqjtu.dpta.web.controller.api.admin;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cqjtu.dpta.api.PafCommService;
import com.cqjtu.dpta.api.RefundRService;
import com.cqjtu.dpta.api.ShpCommService;
import com.cqjtu.dpta.common.lang.Const;
import com.cqjtu.dpta.common.lang.Range;
import com.cqjtu.dpta.common.result.ResultCodeEnum;
import com.cqjtu.dpta.common.util.DptaUtils;
import com.cqjtu.dpta.common.util.PageQueryUtil;
import com.cqjtu.dpta.common.util.ResultUtils;
import com.cqjtu.dpta.dao.entity.CommR;
import com.cqjtu.dpta.dao.entity.PafComm;
import com.cqjtu.dpta.dao.entity.RefundR;
import com.cqjtu.dpta.common.result.Result;
import com.cqjtu.dpta.dao.entity.ShpComm;
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
 * 退款规则表 前端控制器
 * </p>
 *
 * @author mumu
 * @since 2021-04-13
 */
@RestController
@RequestMapping("/platform/api/refund-rule")
public class RefundRController {

    @Resource
    private RefundRService refundRService;

    private static final String[] COLUMNS = {"REFUND_NM", "TYPE", "STATE"};

    /**
     * 根据绑定量排名
     * @param pageable
     * @return
     */
    @GetMapping("sort")
    public Result sort(@PageableDefault Pageable pageable) {
        IPage<RefundR> page = refundRService.bindSort(pageable);
        return Result.ok(page);
    }

    /**
     * 模糊搜索
     * @param pageable
     * @param keyword
     * @param option 按对应条件搜索：0是名称，1是类型，2是状态
     * @return
     */
    @GetMapping("search")
    public Result search(@PageableDefault Pageable pageable,
                         @RequestParam("keyword") String keyword,
                         @RequestParam(value = "option", required = false, defaultValue = "0") Integer option) {
        QueryWrapper<RefundR> queryWrapper = new QueryWrapper<>();
        if (!DptaUtils.in02(option)) {
            return ResultUtils.keyError();
        }
        queryWrapper.like(COLUMNS[option], keyword);
        IPage<RefundR> page = refundRService.page(pageable, queryWrapper);
        return Result.ok(page);
    }

    @GetMapping("findByNm")
    public Result findByNm(@RequestParam Map<String, Object> params) {
        String name = params.get("name").toString();
        if(name.equals("*"))
            name = "";
        PageQueryUtil pageUtil = new PageQueryUtil(params);
        return Result.ok(refundRService.getCommRByName(pageUtil,name));
    }

    /**
     * 获取全部数据
     * @return
     */
    @GetMapping("all")
    public Result all() {
        List<RefundR> list = refundRService.list();
        return Result.ok(list);
    }

    @GetMapping
    public Result page(@PageableDefault Pageable pageable) {
        IPage<RefundR> page = refundRService.page(pageable);
        return Result.ok(page);
    }

    @PostMapping("modif")
    public Result modif(@RequestBody RefundR refundR) {
        boolean result = refundRService.updateById(refundR);
        return Result.judge(result);
    }

    @PostMapping("add")
    public Result add(@RequestBody RefundR refundR) {
        boolean result = refundRService.save(refundR);
        return Result.judge(result);
    }

    @PostMapping("del")
    public Result del(@RequestBody List ids) {
        int sus = refundRService.delList(ids);
        if (ids.size() == sus) {
            return Result.ok();
        }
        if (sus == 0) {
            return Result.build(252,"删除失败\n绑定了商品的退款规则不能被删除");
        }
        int fai = ids.size()-sus;
        return Result.build(251,"成功删除"+sus+"条退款规则\n"+fai+"条删除失败\n绑定了商品的退款规则不能被删除");
    }

    @PostMapping("enable")
    public Result enable(@RequestBody List<Long> ids) {
        List<RefundR> list = new ArrayList<>();
        for (Long id : ids) {
            RefundR refundR = refundRService.getById(id);
            refundR.setState(Const.ENABLE);
            list.add(refundR);
        }
        boolean b = refundRService.updateBatchById(list);
        return Result.judge(b);
    }

    @Resource
    ShpCommService shpCommService;
    @Resource
    PafCommService pafCommService;
    @PostMapping("disable")
    public Result disable(@RequestBody List<Long> ids) {
        List<RefundR> list = new ArrayList<>();
        for (Long id : ids) {
            QueryWrapper<PafComm> wrapper = new QueryWrapper<>();
            wrapper.eq("refund_id",id);
            // 将绑定该规则的平台商品下架，并解除绑定
            List<PafComm> list1 = pafCommService.list(wrapper);
            for (PafComm comm : list1) {
                comm.setState(Const.OUTSELL);
                comm.setRefundId(0L);
                QueryWrapper<ShpComm> wrapper1 = new QueryWrapper<>();
                wrapper1.eq("comm_id",comm.getCommId());
                List<ShpComm> list2 = shpCommService.list(wrapper1);
                for (ShpComm shpComm : list2) {
                    shpComm.setState(Const.OUTSELL);
                }
                shpCommService.updateBatchById(list2);
            }
            pafCommService.updateBatchById(list1);
            RefundR refundR = refundRService.getById(id);
            refundR.setState(Const.DISABLE);
            list.add(refundR);
        }
        boolean b = refundRService.updateBatchById(list);
        return Result.judge(b);
    }

    @GetMapping("getEnableR")
    public List<RefundR> getEnableR () {
        QueryWrapper<RefundR> wrapper = new QueryWrapper<>();
        wrapper.eq("state",Const.ENABLE);
        return refundRService.list(wrapper);
    }
}
