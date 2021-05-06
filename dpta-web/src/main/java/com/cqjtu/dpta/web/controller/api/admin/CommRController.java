package com.cqjtu.dpta.web.controller.api.admin;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cqjtu.dpta.api.CommRService;
import com.cqjtu.dpta.api.PafCommService;
import com.cqjtu.dpta.api.ShpCommService;
import com.cqjtu.dpta.common.lang.Const;
import com.cqjtu.dpta.common.result.ResultCodeEnum;
import com.cqjtu.dpta.common.util.DptaUtils;
import com.cqjtu.dpta.common.util.PageQueryUtil;
import com.cqjtu.dpta.common.util.ResultUtils;
import com.cqjtu.dpta.common.util.Status;
import com.cqjtu.dpta.dao.entity.CommR;
import com.cqjtu.dpta.common.result.Result;
import com.cqjtu.dpta.dao.entity.PafComm;
import com.cqjtu.dpta.dao.entity.ShpComm;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;


import javax.annotation.Resource;
import javax.websocket.server.PathParam;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 佣金规则表 前端控制器
 * </p>
 *
 * @author mumu
 * @since 2021-04-13
 */
@RestController
@RequestMapping("/platform/api/paf-comm-rule")
public class CommRController {

    @Resource
    private CommRService commRService;

    @Resource
    private PafCommService pafCommService;

    private static final String[] COLUMNS = {"R_COMM_NM", "TYPE", "STATE"};
    private static final Integer INSELL = Status.INSELL.value();

    /**
     * 根据商品id上架商品
     *
     * @param id
     * @return
     */
    @GetMapping("insell/{id}")
    public Result insell(@PathVariable Long id) {
        boolean b = inOrOutSell(id, Const.INSELL);
        return Result.ok(b);
    }

    /**
     * 根据商品id下架商品
     *
     * @param id
     * @return
     */
    @GetMapping("outsell/{id}")
    public Result outsell(@PathVariable Long id) {
        boolean b = inOrOutSell(id, Const.OUTSELL);
        return Result.ok(b);
    }

    private boolean inOrOutSell(Long id, int option) {
        PafComm comm = pafCommService.getById(id);
        comm.setState(option);
        return pafCommService.updateById(comm);
    }

    /**
     * 根据佣金规则id按页获取绑定了该佣金规则的商品数据
     *
     * @param id
     * @param
     * @return
     */
    @GetMapping("detail/{id}")
    public Result detail(@PathVariable Long id,
                         @RequestParam Map<String, Object> params) {
        CommR commR = commRService.getById(id);
        if (commR == null) {
            return ResultUtils.keyError();
        }
        if (StringUtils.isEmpty(params.get("page")) || StringUtils.isEmpty(params.get("limit"))) {
            return Result.build(ResultCodeEnum.PARAM_ERROR);
        }
        PageQueryUtil pageUtil = new PageQueryUtil(params);
        return Result.ok(commRService.getPafComm(pageUtil,id));
    }

    /**
     * 根据佣金规则id修改佣金状态
     * 当禁用佣金id时，下架绑定了该佣金规则的商品
     *
     * @param id
     * @param state
     * @return
     */
    @GetMapping("state/{id}/{state}")
    public Result state(@PathVariable Long id,
                        @PathVariable Integer state) {
        CommR commR = commRService.getById(id);
        if (commR == null || state < 0 || state > 1) {
            return Result.build(ResultCodeEnum.KEY_ERROR);
        }
        boolean updateR = true;
        if (state == 0) {
            commR.setState(0);
            UpdateWrapper<PafComm> updateWrapper = new UpdateWrapper<>();
            updateWrapper
                    .like("R_COMM_ID", id)
                    .set("STATE", 0);
            updateR = pafCommService.update(updateWrapper);
        } else {
            commR.setState(1);
        }
        boolean b = commRService.updateById(commR);
        return Result.ok(b && updateR);
    }

    @GetMapping("sort")
    public Result sort(@PageableDefault Pageable pageable) {
        IPage<CommR> page = commRService.bindSort(pageable);
        return Result.ok(page);
    }

    /**
     * 根据佣金规则名称、状态、类型模糊搜索
     *
     * @param pageable
     * @param keyword
     * @param option   0表示按名称模糊搜索，1表示按类型搜索，2表示按状态搜索
     * @return
     */
    @GetMapping("search")
    public Result search(@PageableDefault Pageable pageable,
                         @RequestParam("keyword") String keyword,
                         @RequestParam(value = "option", required = false, defaultValue = "0") Integer option) {
        QueryWrapper<CommR> queryWrapper = new QueryWrapper<>();
        if (!DptaUtils.in02(option)) {
            return ResultUtils.keyError();
        }
        queryWrapper.like(COLUMNS[option], keyword);
        IPage<CommR> page = commRService.page(pageable, queryWrapper);
        return Result.ok(page);
    }

    /**
     * 获取所有商品规则
     *
     * @return
     */
    @GetMapping("all")
    public Result all(@RequestParam Map<String, Object> params) {
        if (StringUtils.isEmpty(params.get("page")) || StringUtils.isEmpty(params.get("limit"))) {
            return Result.build(ResultCodeEnum.PARAM_ERROR);
        }
        PageQueryUtil pageUtil = new PageQueryUtil(params);
        return Result.ok(commRService.getCommRPage(pageUtil));
    }

    @GetMapping("search0/{name}")
    public Result search(@RequestParam Map<String, Object> params,
                         @PathVariable String name) {
        if (StringUtils.isEmpty(name)) {
            return Result.build(ResultCodeEnum.PARAM_ERROR);
        }
        PageQueryUtil pageUtil = new PageQueryUtil(params);
        return Result.ok(commRService.getCommRByName(pageUtil,name));
    }

    @GetMapping
    public Result page(@PageableDefault Pageable pageable) {
        IPage<CommR> page = commRService.page(pageable);
        return Result.ok(page);
    }

    @PostMapping("modif")
    public Result modif(@RequestBody CommR commR) {
        boolean result = commRService.updateById(commR);
        return Result.judge(result);
    }

    @PostMapping("add")
    public Result add(@RequestBody CommR commR) {
        boolean result = commRService.save(commR);
        return Result.judge(result);
    }

    @PostMapping("del")
    public Result del(@RequestBody List<Long> ids) {
        int sus = commRService.delList(ids);
        if (ids.size() == sus) {
            return Result.ok();
        }
        if (sus == 0) {
            return Result.build(252,"删除失败\n绑定了商品的佣金规则不能被删除");
        }
        int fai = ids.size()-sus;
        return Result.build(251,"成功删除"+sus+"条佣金规则\n"+fai+"条删除失败\n绑定了商品的佣金规则不能被删除");
    }
    @PostMapping("enable")
    public Result enable(@RequestBody List<Long> ids) {
        List<CommR> list = new ArrayList<>();
        for (Long id : ids) {
            CommR commR = commRService.getById(id);
            commR.setState(Const.ENABLE);
            list.add(commR);
        }
        boolean b = commRService.updateBatchById(list);
        return Result.judge(b);
    }
    @Resource
    ShpCommService shpCommService;
    @PostMapping("disable")
    public Result disable(@RequestBody List<Long> ids) {
        List<CommR> list = new ArrayList<>();
        for (Long id : ids) {
            QueryWrapper<PafComm> wrapper = new QueryWrapper<>();
            wrapper.eq("r_comm_id",id);
            // 将绑定该规则的平台商品下架，并解除绑定
            List<PafComm> list1 = pafCommService.list(wrapper);
            for (PafComm comm : list1) {
                comm.setState(Const.OUTSELL);
                comm.setRCommId(null);
                QueryWrapper<ShpComm> wrapper1 = new QueryWrapper<>();
                wrapper1.eq("comm_id",comm.getCommId());
                List<ShpComm> list2 = shpCommService.list(wrapper1);
                for (ShpComm shpComm : list2) {
                    shpComm.setState(Const.OUTSELL);
                }
                shpCommService.updateBatchById(list2);
            }
            pafCommService.updateBatchById(list1);
            CommR commR = commRService.getById(id);
            commR.setState(Const.DISABLE);
            list.add(commR);
        }
        boolean b = commRService.updateBatchById(list);
        return Result.judge(b);
    }

}
