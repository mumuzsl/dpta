package com.cqjtu.dpta.web.controller.api.admin;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cqjtu.dpta.api.CommRService;
import com.cqjtu.dpta.api.PafCommService;
import com.cqjtu.dpta.api.support.SettleService;
import com.cqjtu.dpta.dao.entity.PafComm;
import com.cqjtu.dpta.common.result.Result;
import com.cqjtu.dpta.dao.entity.Shop;
import com.cqjtu.dpta.dao.entity.ShopTop;
import io.swagger.models.auth.In;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.ArrayList;
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

    @Resource
    private SettleService settleService;

    @Resource
    private CommRService commRService;

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

    @RequestMapping("/show/bar")
    @ResponseBody
    public List<Integer> findBybar(Model model){
        List barData = new ArrayList();
        barData.add(settleService.platSum(2019));
        barData.add(settleService.platSum(2020));
        barData.add(settleService.platSum(2021));

        return barData;
    }

    @RequestMapping("/show/store")
    @ResponseBody
    public List<Object> findByStore(){
        List store  = new ArrayList();
        List<String> names = new ArrayList<>();
        List<BigDecimal> prices = new ArrayList<>();
        String a = pafCommService.getDistrSumM(2021).get(0).getDistrName();
        String b = pafCommService.getDistrSumM(2021).get(1).getDistrName();
        String c = pafCommService.getDistrSumN(2021).get(0).getDistrName();
        String d = pafCommService.getDistrSumN(2021).get(1).getDistrName();
        names.add(a);
        names.add(b);
        names.add(c);
        names.add(d);
        for(int i=2016;i<=2021;i++){
            prices.add(pafCommService.getDistrSum(a,i));
            prices.add(pafCommService.getDistrSum(b,i));
            prices.add(pafCommService.getDistrSum(c,i));
            prices.add(pafCommService.getDistrSum(d,i));
        }
        store.add(names);
        store.add(prices);
        return store;
    }

    @RequestMapping("/topComm")
    @ResponseBody
    public List<Object> topComm(Model model){
        List data = new ArrayList<>();
        List<String> commNames = new ArrayList<>();
        List<String> specs = new ArrayList<>();
        List<BigDecimal> rePrices = new ArrayList<>();
        List<Integer> saVolumes = new ArrayList<>();
        for (int i=0;i<pafCommService.getShopTop().size();i++){
            commNames.add(pafCommService.getShopTop().get(i).getCommName());
            specs.add(pafCommService.getShopTop().get(i).getSpec());
            rePrices.add(pafCommService.getShopTop().get(i).getRePrice());
            saVolumes.add(pafCommService.getShopTop().get(i).getSaVolume());
        }
        data.add(commNames);
        data.add(specs);
        data.add(rePrices);
        data.add(saVolumes);
        return data;
    }

    @RequestMapping("/topDistr")
    @ResponseBody
    public List<Object> topDistr(){
        List data = new ArrayList<>();
        List<String> distrNms = new ArrayList<>();
        List<BigDecimal> comPs = new ArrayList<>();
        List<String> commNames = new ArrayList<>();
        for (int i = 0;i<pafCommService.getDistrTop().size();i++){
            distrNms.add(pafCommService.getDistrTop().get(i).getDistrNm());

            commNames.add(pafCommService.getMaxShop(pafCommService.getDistrTop().get(i).getDistrNm()));

            comPs.add(pafCommService.getDistrTop().get(i).getComP());
        }
        data.add(distrNms);
//        data.add(commNames);
        data.add(comPs);
        return data;
    }

    @RequestMapping("/show/pie")
    @ResponseBody
    public List<Object> findByPie(){
        List data = new ArrayList();

        for (int i = 0;i<commRService.getAllCommR().size();i++){
            data.add(commRService.getAllCommR().get(i));
        }

        return data;
    }

    @GetMapping("plat_analysis")
    public String plat_analysisPage(HttpServletRequest request, Model model) {
        request.setAttribute("path", "plat_analysis");
//        Integer shopSum = newBeeMallUserService.shopSum();
//        Integer orderSum = newBeeMallOrderService.orderSum();
        model.addAttribute("shopSum",1);
        model.addAttribute("orderSum",2);

        return "url";
    }

}
