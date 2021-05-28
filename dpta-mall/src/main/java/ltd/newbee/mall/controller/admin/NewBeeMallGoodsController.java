/**
 * 严肃声明：
 * 开源版本请务必保留此注释头信息，若删除我方将保留所有法律责任追究！
 * 本系统已申请软件著作权，受国家版权局知识产权以及国家计算机软件著作权保护！
 * 可正常分享和学习源码，不得用于违法犯罪活动，违者必究！
 * Copyright (c) 2019-2020 十三 all rights reserved.
 * 版权所有，侵权必究！
 */
package ltd.newbee.mall.controller.admin;

import com.cqjtu.dpta.dao.entity.CommR;
import com.cqjtu.dpta.dao.entity.PafComm;
import com.cqjtu.dpta.dao.entity.RefundR;
import com.cqjtu.dpta.dao.entity.Supp;
import ltd.newbee.mall.common.Constants;
import ltd.newbee.mall.common.NewBeeMallCategoryLevelEnum;
import ltd.newbee.mall.common.ServiceResultEnum;
import ltd.newbee.mall.entity.GoodsCategory;
import ltd.newbee.mall.entity.NewBeeMallGoods;
import ltd.newbee.mall.service.NewBeeMallCategoryService;
import ltd.newbee.mall.service.NewBeeMallGoodsService;
import ltd.newbee.mall.util.PageQueryUtil;
import ltd.newbee.mall.util.Result;
import ltd.newbee.mall.util.ResultGenerator;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.sql.Array;
import java.util.*;

@Controller
@RequestMapping("/admin")
public class NewBeeMallGoodsController {

    @Resource
    private NewBeeMallGoodsService newBeeMallGoodsService;
    @Resource
    private NewBeeMallCategoryService newBeeMallCategoryService;
    @Resource
    RestTemplate restTemplate;

    @GetMapping("/goods")
    public String goodsPage(HttpServletRequest request) {
        request.setAttribute("path", "newbee_mall_goods");
        return "admin/newbee_mall_goods";
    }

    @GetMapping("/goods/edit")
    public String edit(HttpServletRequest request) {
        request.setAttribute("path", "edit");
        // 获取所有供应商
        List<Supp> supp = restTemplate.getForObject("http://localhost:8081//api/data/supp/all",List.class);
        List<CommR> commRs = restTemplate.getForObject("http://localhost:8081/platform/api/paf-comm-rule/getEnableR",List.class);
        List<RefundR> refundRs = restTemplate.getForObject("http://localhost:8081/platform/api/refund-rule/getEnableR",List.class);

        //查询所有的一级分类
        List<GoodsCategory> firstLevelCategories = newBeeMallCategoryService.selectByLevelAndParentIdsAndNumber(Collections.singletonList(0L), NewBeeMallCategoryLevelEnum.LEVEL_ONE.getLevel());
        if (!CollectionUtils.isEmpty(firstLevelCategories)) {
            //查询一级分类列表中第一个实体的所有二级分类
            List<GoodsCategory> secondLevelCategories = newBeeMallCategoryService.selectByLevelAndParentIdsAndNumber(Collections.singletonList(firstLevelCategories.get(0).getCategoryId()), NewBeeMallCategoryLevelEnum.LEVEL_TWO.getLevel());
            if (!CollectionUtils.isEmpty(secondLevelCategories)) {
                //查询二级分类列表中第一个实体的所有三级分类
                List<GoodsCategory> thirdLevelCategories = newBeeMallCategoryService.selectByLevelAndParentIdsAndNumber(Collections.singletonList(secondLevelCategories.get(0).getCategoryId()), NewBeeMallCategoryLevelEnum.LEVEL_THREE.getLevel());
                request.setAttribute("firstLevelCategories", firstLevelCategories);
                request.setAttribute("secondLevelCategories", secondLevelCategories);
                request.setAttribute("thirdLevelCategories", thirdLevelCategories);
                request.setAttribute("suppers",supp);
                request.setAttribute("commRs",commRs);
                request.setAttribute("refundRs",refundRs);
                request.setAttribute("path", "goods-edit");
                return "admin/newbee_mall_goods_edit";
            }
        }
        return "error/error_5xx";
    }

    @GetMapping("/goods/edit/{goodsId}")
    public String edit(HttpServletRequest request, @PathVariable("goodsId") Long goodsId) {
        request.setAttribute("path", "edit");
        NewBeeMallGoods newBeeMallGoods = newBeeMallGoodsService.getNewBeeMallGoodsById(goodsId);
        PafComm pafComm = restTemplate.getForObject("http://localhost:8081/platform/api/paf-comm/getById?pafCommId="+goodsId,PafComm.class);
        if (newBeeMallGoods == null) {
            return "error/error_400";
        }
        // 获取所有供应商
        List<Supp> supp = restTemplate.getForObject("http://localhost:8081/api/data/supp/all",List.class);
        List<CommR> commRs = restTemplate.getForObject("http://localhost:8081/platform/api/paf-comm-rule/getEnableR",List.class);
        List<RefundR> refundRs = restTemplate.getForObject("http://localhost:8081/platform/api/refund-rule/getEnableR",List.class);
        if (newBeeMallGoods.getGoodsCategoryId() > 0) {
            if (newBeeMallGoods.getGoodsCategoryId() != null || newBeeMallGoods.getGoodsCategoryId() > 0) {
                //有分类字段则查询相关分类数据返回给前端以供分类的三级联动显示
                GoodsCategory currentGoodsCategory = newBeeMallCategoryService.getGoodsCategoryById(newBeeMallGoods.getGoodsCategoryId());
                //商品表中存储的分类id字段为三级分类的id，不为三级分类则是错误数据
                if (currentGoodsCategory != null && currentGoodsCategory.getCategoryLevel() == NewBeeMallCategoryLevelEnum.LEVEL_THREE.getLevel()) {
                    //查询所有的一级分类
                    List<GoodsCategory> firstLevelCategories = newBeeMallCategoryService.selectByLevelAndParentIdsAndNumber(Collections.singletonList(0L), NewBeeMallCategoryLevelEnum.LEVEL_ONE.getLevel());
                    //根据parentId查询当前parentId下所有的三级分类
                    List<GoodsCategory> thirdLevelCategories = newBeeMallCategoryService.selectByLevelAndParentIdsAndNumber(Collections.singletonList(currentGoodsCategory.getParentId()), NewBeeMallCategoryLevelEnum.LEVEL_THREE.getLevel());
                    //查询当前三级分类的父级二级分类
                    GoodsCategory secondCategory = newBeeMallCategoryService.getGoodsCategoryById(currentGoodsCategory.getParentId());
                    if (secondCategory != null) {
                        //根据parentId查询当前parentId下所有的二级分类
                        List<GoodsCategory> secondLevelCategories = newBeeMallCategoryService.selectByLevelAndParentIdsAndNumber(Collections.singletonList(secondCategory.getParentId()), NewBeeMallCategoryLevelEnum.LEVEL_TWO.getLevel());
                        //查询当前二级分类的父级一级分类
                        GoodsCategory firestCategory = newBeeMallCategoryService.getGoodsCategoryById(secondCategory.getParentId());
                        if (firestCategory != null) {
                            //所有分类数据都得到之后放到request对象中供前端读取
                            request.setAttribute("firstLevelCategories", firstLevelCategories);
                            request.setAttribute("secondLevelCategories", secondLevelCategories);
                            request.setAttribute("thirdLevelCategories", thirdLevelCategories);
                            request.setAttribute("firstLevelCategoryId", firestCategory.getCategoryId());
                            request.setAttribute("secondLevelCategoryId", secondCategory.getCategoryId());
                            request.setAttribute("thirdLevelCategoryId", currentGoodsCategory.getCategoryId());
                        }
                    }
                }
            }
        }
        if (newBeeMallGoods.getGoodsCategoryId() == 0) {
            //查询所有的一级分类
            List<GoodsCategory> firstLevelCategories = newBeeMallCategoryService.selectByLevelAndParentIdsAndNumber(Collections.singletonList(0L), NewBeeMallCategoryLevelEnum.LEVEL_ONE.getLevel());
            if (!CollectionUtils.isEmpty(firstLevelCategories)) {
                //查询一级分类列表中第一个实体的所有二级分类
                List<GoodsCategory> secondLevelCategories = newBeeMallCategoryService.selectByLevelAndParentIdsAndNumber(Collections.singletonList(firstLevelCategories.get(0).getCategoryId()), NewBeeMallCategoryLevelEnum.LEVEL_TWO.getLevel());
                if (!CollectionUtils.isEmpty(secondLevelCategories)) {
                    //查询二级分类列表中第一个实体的所有三级分类
                    List<GoodsCategory> thirdLevelCategories = newBeeMallCategoryService.selectByLevelAndParentIdsAndNumber(Collections.singletonList(secondLevelCategories.get(0).getCategoryId()), NewBeeMallCategoryLevelEnum.LEVEL_THREE.getLevel());
                    request.setAttribute("firstLevelCategories", firstLevelCategories);
                    request.setAttribute("secondLevelCategories", secondLevelCategories);
                    request.setAttribute("thirdLevelCategories", thirdLevelCategories);
                }
            }
        }
        Long suppId = pafComm == null ? null : pafComm.getSuppId();
        Long rCommId = pafComm == null ? null : pafComm.getRCommId();
        Long refundId = pafComm == null ? null : pafComm.getRefundId();
        request.setAttribute("suppId",suppId);
        request.setAttribute("rCommId",rCommId);
        request.setAttribute("refundId",refundId);
        request.setAttribute("suppers",supp);
        request.setAttribute("commRs",commRs);
        request.setAttribute("refundRs",refundRs);
        request.setAttribute("goods", newBeeMallGoods);
        request.setAttribute("path", "goods-edit");
        return "admin/newbee_mall_goods_edit";
    }

    /**
     * 列表
     */
    @RequestMapping(value = "/goods/list", method = RequestMethod.GET)
    @ResponseBody
    public Result list(@RequestParam Map<String, Object> params) {
        if (StringUtils.isEmpty(params.get("page")) || StringUtils.isEmpty(params.get("limit"))) {
            return ResultGenerator.genFailResult("参数异常！");
        }
        PageQueryUtil pageUtil = new PageQueryUtil(params);
        return ResultGenerator.genSuccessResult(newBeeMallGoodsService.getNewBeeMallGoodsPage(pageUtil));
    }

    /**
     * 添加
     */
    @RequestMapping(value = "/goods/save", method = RequestMethod.POST)
    @ResponseBody
    public Result save(@RequestBody NewBeeMallGoods newBeeMallGoods) {
        if (StringUtils.isEmpty(newBeeMallGoods.getGoodsName())
                || StringUtils.isEmpty(newBeeMallGoods.getGoodsIntro())
                || StringUtils.isEmpty(newBeeMallGoods.getTag())
                || Objects.isNull(newBeeMallGoods.getOriginalPrice())
                || Objects.isNull(newBeeMallGoods.getGoodsCategoryId())
                || Objects.isNull(newBeeMallGoods.getSellingPrice())
                || Objects.isNull(newBeeMallGoods.getStockNum())
                || Objects.isNull(newBeeMallGoods.getGoodsSellStatus())
                || StringUtils.isEmpty(newBeeMallGoods.getGoodsCoverImg())
                || StringUtils.isEmpty(newBeeMallGoods.getGoodsDetailContent())) {
            return ResultGenerator.genFailResult("参数异常！");
        }
        PafComm pafComm = new PafComm();
        pafComm.setCommId(newBeeMallGoods.getGoodsId());
        pafComm.setState(newBeeMallGoods.getGoodsSellStatus().intValue());
        pafComm.setCategoryId(newBeeMallGoods.getGoodsCategoryId());
        pafComm.setCommD(newBeeMallGoods.getGoodsIntro());
        pafComm.setCommNm(newBeeMallGoods.getGoodsName());
        pafComm.setImgUrl(newBeeMallGoods.getGoodsCoverImg());
        pafComm.setSuppId(newBeeMallGoods.getSuppId());
        pafComm.setSuppPrice(new BigDecimal(newBeeMallGoods.getSellingPrice()));
        pafComm.setRefundId(newBeeMallGoods.getRefundId());
        pafComm.setRCommId(newBeeMallGoods.getrCommId());

        Boolean bol = restTemplate.postForObject("http://localhost:8081/platform/api/paf-comm/add",pafComm,Boolean.class);
        String result = newBeeMallGoodsService.saveNewBeeMallGoods(newBeeMallGoods);
        if (ServiceResultEnum.SUCCESS.getResult().equals(result) && bol == true) {
            return ResultGenerator.genSuccessResult();
        } else {
            return ResultGenerator.genFailResult(result);
        }
    }


    /**
     * 修改
     */
    @RequestMapping(value = "/goods/update", method = RequestMethod.POST)
    @ResponseBody
    public Result update(@RequestBody NewBeeMallGoods newBeeMallGoods) {
        if (Objects.isNull(newBeeMallGoods.getGoodsId())
                || StringUtils.isEmpty(newBeeMallGoods.getGoodsName())
                || StringUtils.isEmpty(newBeeMallGoods.getGoodsIntro())
                || StringUtils.isEmpty(newBeeMallGoods.getTag())
                || Objects.isNull(newBeeMallGoods.getOriginalPrice())
                || Objects.isNull(newBeeMallGoods.getSellingPrice())
                || Objects.isNull(newBeeMallGoods.getGoodsCategoryId())
                || Objects.isNull(newBeeMallGoods.getStockNum())
                || Objects.isNull(newBeeMallGoods.getGoodsSellStatus())
                || StringUtils.isEmpty(newBeeMallGoods.getGoodsCoverImg())
                || StringUtils.isEmpty(newBeeMallGoods.getGoodsDetailContent())) {
            return ResultGenerator.genFailResult("参数异常！");
        }
        PafComm pafComm = new PafComm();
        pafComm.setCommId(newBeeMallGoods.getGoodsId());
        pafComm.setState(newBeeMallGoods.getGoodsSellStatus().intValue());
        pafComm.setCategoryId(newBeeMallGoods.getGoodsCategoryId());
        pafComm.setCommD(newBeeMallGoods.getGoodsIntro());
        pafComm.setCommNm(newBeeMallGoods.getGoodsName());
        pafComm.setImgUrl(newBeeMallGoods.getGoodsCoverImg());
        pafComm.setSuppId(newBeeMallGoods.getSuppId());
        pafComm.setSuppPrice(new BigDecimal(newBeeMallGoods.getSellingPrice()));
        pafComm.setRefundId(newBeeMallGoods.getRefundId());
        pafComm.setRCommId(newBeeMallGoods.getrCommId());

        Boolean bol = restTemplate.postForObject("http://localhost:8081/platform/api/paf-comm/modif",pafComm,Boolean.class);
        String result = newBeeMallGoodsService.updateNewBeeMallGoods(newBeeMallGoods);
        if (ServiceResultEnum.SUCCESS.getResult().equals(result) && bol == true) {
            return ResultGenerator.genSuccessResult();
        } else {
            return ResultGenerator.genFailResult(result);
        }
    }

    /**
     *删除
     */
    @RequestMapping(value = "/goods/delete",method = RequestMethod.POST)
    @ResponseBody
    public Result delete(@RequestBody Long[] ids){
        System.out.println(ids.length);
        if (ids.length< 1) {
            return ResultGenerator.genFailResult("参数异常");
        }
        if (newBeeMallGoodsService.deleteBatch(ids)) {
            System.out.println("jinru");
            System.out.println(ResultGenerator.genSuccessResult());
            return ResultGenerator.genSuccessResult();
        }else {
            return ResultGenerator.genFailResult("删除失败");
        }
    }

    /**
     * 详情
     */
    @GetMapping("/goods/info/{id}")
    @ResponseBody
    public Result info(@PathVariable("id") Long id) {
        NewBeeMallGoods goods = newBeeMallGoodsService.getNewBeeMallGoodsById(id);
        if (goods == null) {
            return ResultGenerator.genFailResult(ServiceResultEnum.DATA_NOT_EXIST.getResult());
        }
        return ResultGenerator.genSuccessResult(goods);
    }

    /**
     * 批量修改销售状态
     */
    @RequestMapping(value = "/goods/status/{sellStatus}", method = RequestMethod.POST)
    @ResponseBody
    public Result changeSellStatus(@RequestBody List<Long> ids, @PathVariable("sellStatus") int sellStatus) {
        if (ids.size() < 1) {
            return ResultGenerator.genFailResult("参数异常！");
        }
        if (sellStatus != Constants.SELL_STATUS_UP && sellStatus != Constants.SELL_STATUS_DOWN) {
            return ResultGenerator.genFailResult("状态异常！");
        }
        if (sellStatus==Constants.SELL_STATUS_UP){
            int count = 0;
            List<Long> list = new ArrayList<>();
            for (Long id : ids) {
                PafComm pafComm =  restTemplate.getForObject("http://localhost:8081/platform/api/paf-comm/getById?pafCommId="+id,PafComm.class);
                if (pafComm.getRCommId() == null || pafComm.getRefundId()==null ||pafComm.getRCommId().equals(0L) || pafComm.getRefundId().equals(0L)) {
                    count++;
                    continue;
                }
                list.add(id);
            }
            if(count < ids.size()&&newBeeMallGoodsService.batchUpdateSellStatus(list.toArray(new Long[list.size()]),sellStatus)){
                restTemplate.postForObject("http://localhost:8081/platform/api/paf-comm/changState/"+1,list,Boolean.class);
                if(count == 0){
                    return ResultGenerator.genSuccessResult();
                }else {
                    return ResultGenerator.genFailResult("有"+count+"个商品没有绑定佣金规则或退款规则，不能启用");
                }
            }else {
                return ResultGenerator.genFailResult("上架失败");
            }
        }

        if (newBeeMallGoodsService.batchUpdateSellStatus(ids.toArray(new Long[ids.size()]), sellStatus)) {
            restTemplate.postForObject("http://localhost:8081/platform/api/paf-comm/changState/"+0,ids,Boolean.class);
            return ResultGenerator.genSuccessResult();
        } else {
            return ResultGenerator.genFailResult("下架失败");
        }
    }

}